package com.newland.beecode.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tempuri.service.MMSService;

import com.newland.beecode.dao.CouponDao;
import com.newland.beecode.dao.MarketingActDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MmsTemplate;
import com.newland.beecode.domain.condition.CheckResult;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.BarCodeService;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.MarketingActService;
import com.newland.utils.FilesHelper;
import com.newland.utils.NewlandUtil;
import com.newland.utils.UuidHelper;
import com.ws.util.service.SMSService;

import javax.annotation.Resource;

@Service(value = "marketingActService")
public class MarketingActServiceImpl implements MarketingActService {
    
    @Resource(name = "marketingActDao")
    private MarketingActDao actDao;
    
    @Resource(name = "couponDao")
    private CouponDao couponDao;
    
	private Log logger = LogFactory.getLog(MarketingActServiceImpl.class);
	private List<MarketingAct> giveActs = new LinkedList<MarketingAct>();
	private List<MarketingAct> giveActsSms=new LinkedList<MarketingAct>();
	@Autowired
	private BarCodeService barCodeService;
	@Autowired
	private MMSService mmsService;
	@Autowired
	private CheckService checkService;
	@Autowired
	private SMSService smsService;
	private Thread mmsWorker = new Thread() {
		public void run() {

			while (true) {
				try {
					synchronized (giveActs) {
						if (giveActs.isEmpty()) {
							giveActs.wait();
						}
					}
					MarketingAct act=giveActs.remove(0);
					/**
					 * 彩信发送
					 */ 
					long[] count=sendMMS(act);
					act.setMmsSendSum(count[0]);
					act.setSmsSendSum(count[1]);
					act.setActStatus(MarketingAct.STATUS_AFTER_GIVE);
					//act.merge();
                                        actDao.update(act);
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}

		};
	};
	public MarketingActServiceImpl() {
		mmsWorker.start();
	}

	/**
	 * 同步块，防止多次生成
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public long checkMarketingAct(Long actNo, Integer actStatus)throws AppException{
		long count=0;
		MarketingAct act;
		synchronized(this){
			//act = MarketingAct.findMarketingAct(actNo);
                        act = actDao.findUniqueByProperty("actNo", actNo);
			if(!act.getActStatus().equals(MarketingAct.STATUS_BEFORE_RECHECK)){
				throw new AppException(ErrorsCode.BIZ_COUPON_CHECKED,"");
			}
			act.setActStatus(actStatus);
			if (actStatus .equals( MarketingAct.STATUS_BEFORE_GIVE)) {
			  count=genCoupons(act);
			  act.setActStatus(MarketingAct.STATUS_BEFORE_GIVE);
			}
			//act.merge();
                        actDao.update(act);
		}
		return count;
	}
    public void marketingActSend(Long actNo)throws AppException{
    	
    	synchronized(this){
    	  //MarketingAct act=MarketingAct.findMarketingAct(actNo);
            MarketingAct act= actDao.findUniqueByProperty("actNo", actNo);
    	  if(!act.getActStatus().equals(MarketingAct.STATUS_BEFORE_GIVE)){
    		 throw new AppException(ErrorsCode.BIZ_MARKETING_GIVED,"");
    	  }
    	  long balance=0;
    	  try {
    		  balance =this.mmsService.getBalance("GD-ltwx", "l123", 1);
  		   } catch (Exception e) {
  			    throw new AppException(ErrorsCode.BIZ_MMS_CON_ERROR,"",e);
  		   }
  		 if(balance<act.getCouponSum()){
				//throw new AppException(ErrorsCode.BIZ_MS_BALANCE_UN_LESS,"");
			}
    	  act.setActStatus(MarketingAct.STATUS_SENDNG);
    	  //act=act.merge();
          act = actDao.update(act);
    	  synchronized(giveActs){
      	    giveActs.add(act);
      	    giveActs.notifyAll();
      	  }
    	}
    }
	@Transactional
	public long genCoupons(MarketingAct act) throws AppException {
		long count=0;
		try {
			List<Customer> list=this.checkService.getCustomers(act.getFileName());
			for(Customer customer: list){
				count++;
				Coupon coupon = new Coupon();
				coupon.setAcctMobile(customer.getMobile());
				coupon.setAcctName(customer.getName());
				coupon.setMarketingAct(act);
				coupon.setCouponStatus(Coupon.STATUS_VALID);
				coupon.setSerialNo(UuidHelper.generateUUID());
				coupon.setBusinessType(act.getBizNo());
				coupon.setRebateRate(act.getRebateRate());
				coupon.setTimes(act.getTimes());
				coupon.setRemainTimes(act.getTimes());
				coupon.setGenTime(new Date());
				coupon.setMmsStatus(Coupon.MMS_STATUS_WAIT);
				coupon.setSmsStatus(Coupon.SMS_STATUS_WAIT);
				//coupon.persist();
                                couponDao.save(coupon);
				this.genCode(coupon,act);
				this.genTxtFile(coupon, act);
			}
			act.setCouponSum(count);
			//act.merge();
                        actDao.update(act);
		} catch (Exception e) {
			throw new AppException(ErrorsCode.BIZ_COUPON_GEN_ERROR,"",e);
		}
		return count;
	}
	/**
	 * 二维码生成
	 * @param coupon
	 */
	public void genCode(Coupon coupon,MarketingAct act){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String dateStr = sdf.format(act.getEndDate());
		String content=coupon.getBusinessType()+NewlandUtil.float2StringByRebate(coupon.getRebateRate())+dateStr+coupon.getSerialNo()+act.getBindCard();
		this.barCodeService.genCode(content,coupon.getCouponId().toString());
		
	}
	/**
	 * 彩信文件生成
	 * @param coupon
	 * @param marketingAct
	 */
	public void genTxtFile(Coupon coupon,MarketingAct marketingAct){
		String content=marketingAct.getMmsContent();
		content=content.replaceAll(MmsTemplate.COUPONID, coupon.getCouponId().toString());
		content=content.replaceAll(MmsTemplate.START_DATE,NewlandUtil.dataToString(marketingAct.getStartDate(), "yyyy-MM-dd"));
		content=content.replaceAll(MmsTemplate.END_DATE,NewlandUtil.dataToString(marketingAct.getEndDate(), "yyyy-MM-dd"));
		content=content.replaceAll(MmsTemplate.NAME, coupon.getAcctName());
		FilesHelper.genTextFile(content, coupon.getCouponId());
	}
    public long[] sendMMS(MarketingAct act){
    	//List<Coupon> list=Coupon.findByActNo(act.getActNo());
        List<Coupon> list = couponDao.findByActNo(act.getActNo());
    	System.out.println("send ... count:"+list.size());
    	long time=System.currentTimeMillis();
    	long mmsCount=0;
    	long smsCount=0;
    	for(Coupon coupon:list){
          try {
			byte[] bytes=FilesHelper.getZIPByte(coupon.getCouponId());
			  String[] str=mmsService.sendMMS("GD-ltwx", "l123", coupon.getAcctMobile(), act.getMmsTitle(), bytes);
			  System.out.println("--------->"+str[0]);
			  if(str[0].indexOf("OK")>=0){
				  mmsCount++;
				coupon.setMmsStatus(Coupon.MMS_STATUS_SNED);
				coupon.setMmsId(str[1]);
			  }else{
				 coupon.setMmsStatus(Coupon.MMS_STATUS_SEND_ERROR);
				 coupon.setMmsDesc(str[0]);
			  }
			  String content=FilesHelper.getTextContent(coupon.getCouponId().toString());
			  System.out.println(content);
			  int resp=smsService.sendSMS("2SDK-EMY-6688-JBVTN", "123456", null, new String[]{coupon.getAcctMobile()}, act.getMmsTitle(), content, "gbk",5, coupon.getCouponId());
			 System.out.println("---------------> sms:"+resp);
			  if(resp==0){
				  smsCount++;
					coupon.setSmsStatus(Coupon.SMS_STATUS_SNED);
				  }else{
					 coupon.setSmsStatus(Coupon.SMS_STATUS_SEND_ERROR);
				  }
			  //coupon.merge();
                          couponDao.update(coupon);
		   }catch (Exception e) {
			 logger.error("", e);
		   }
       }
    	System.out.println("total times -------->"+(System.currentTimeMillis()-time));
		return new long[]{mmsCount,smsCount};
    	
    }
	@Transactional
	public void createMarketingAct(MarketingAct act, MultipartFile file)throws AppException {
		String fileName=String.valueOf(System.currentTimeMillis());
		CheckResult cr=this.checkService.customerCheck(file, fileName);
		if(!cr.isPass()){
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_CREATE_ERROR,"");
		}
		act.setFileName(fileName);
		act.setGenTime(new Date());
		act.setActStatus(MarketingAct.STATUS_BEFORE_RECHECK);
		act.setMmsContent(act.getMmsTemplate().getTemplateContent());
		//act.persist();
                actDao.save(act);
	}

	@Transactional
	public void invalidMarketingAct(Long actNo)throws AppException {
		//MarketingAct act = MarketingAct.findMarketingAct(actNo);
            MarketingAct act = actDao.findUniqueByProperty("actNo", actNo);
		if (act.getActStatus() != MarketingAct.STATUS_BEFORE_RECHECK) {
			throw new AppException(ErrorsCode.BIZ_MARKETACT_DONOT_DELETE,"do not delete");
		} else {
			act.setActStatus(MarketingAct.STAUS_DELETE);
			//act.merge();
                        actDao.update(act);
		}
	}

	@Override
	public void append(Long actNo, MultipartFile file) throws AppException {
		//MarketingAct act = MarketingAct.findMarketingAct(actNo);
            MarketingAct act = actDao.findUniqueByProperty("actNo", actNo);
		try {
			FilesHelper.storeFile(file, String.valueOf(actNo));
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_FILE_UPLOAD_ERROR,"");
		}
		synchronized (giveActs) {
			giveActs.add(act);
			giveActs.notifyAll();
		}
	}
	
	/**
	 * 每天零点开始执行批量过期处理任务
	 */
	@Override
	@Transactional
	@Scheduled(cron="0 0 0 * * *")
	public void expiredProc() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			//不可能发生异常
		}
		//MarketingAct.expired(date);
                actDao.expired(date);
	}

	@Override
	public List<MarketingAct> findByCatalog(Long id) {
		return this.actDao.findByProperty("marketingCatalog.id", id);
	}

	@Override
	public MarketingAct findByActNo(Long actNo) {
		List<MarketingAct> list= this.actDao.findByProperty("actNo", actNo);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public QueryResult findByCondition(MarketingActCondition mac) {
		return this.actDao.findMarketingActsByCondition(mac);
	}

	@Override
	public MarketingActSummary marketingSummary(Long actNo) {
		return this.actDao.marketingSummary(actNo);
	}

	@Override
	public QueryResult findMarketingActEntriesByActStatus(Integer actStatus,
			Integer page, Integer size) {
		return this.actDao.findMarketingActEntriesByActStatus(actStatus, page, size);
	}

	@Override
	public List<MarketingAct> findMarketingActEntries(Integer firstResult, Integer maxResults) {
		return this.actDao.findMarketingActEntries(firstResult, maxResults);
	}

	@Override
	public long countMarketingActs() {
		return this.actDao.countMarketingActs();
	}

	@Override
	public List<MarketingAct> findAll() {
	    return this.actDao.findAll();
	}

	@Override
	public List<MarketingAct> findMarketingActsByActStatus(Integer actStatus) {
		return this.actDao.findMarketingActsByActStatus(actStatus);
	}

}
