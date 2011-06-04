package com.newland.beecode.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.newland.beecode.dao.CouponDao;
import com.newland.beecode.dao.CustomerDao;
import com.newland.beecode.dao.MarketingActDao;
import com.newland.beecode.dao.MarketingCatalogDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.MmsTemplate;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.BarCodeService;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.CustomerService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.PartnerService;
import com.newland.beecode.service.SendService;
import com.newland.beecode.task.service.SendInvokeService;
import com.newland.utils.BarCodeFormat;
import com.newland.utils.UuidHelper;

import javax.annotation.Resource;

@Service(value = "marketingActService")
public class MarketingActServiceImpl implements MarketingActService {
    
    @Resource(name = "marketingActDao")
    private MarketingActDao actDao;
    @Resource(name = "marketingCatalogDao")
    private MarketingCatalogDao marketingCatalogDao;
    
    @Resource(name = "couponDao")
    private CouponDao couponDao;
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private PartnerService partnerService;
    
	private Log logger = LogFactory.getLog(MarketingActServiceImpl.class);
	@Autowired
	private BarCodeService barCodeService;
	@Autowired
	private CheckService checkService;
	@Autowired
	private FileService fileService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SendService sendService;
	@Resource(name="smsSendInvokeService")
	private SendInvokeService smsSendInvokeService;
	@Resource(name="mmsSendInvokeService")
	private SendInvokeService mmsSendInvokeService;
	
	/**
	 * 单个客户文件生成码（事务）
	 * @param act
	 * @param customer
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public void checkMarketingActByLimit(MarketingAct act,Customer customer)throws AppException{
		        List<Customer> customers=this.checkService.getCustomer(customer.getNewName());
		        this.genCoupons(act, customers, customer.getId());
	}
    @Transactional(rollbackFor=Exception.class)
	public void append(Long actNo, MultipartFile file, String bizNo) throws AppException {
					MarketingAct act = actDao.findUniqueByProperty("actNo", actNo);
					String newName=act.getActNo().toString()+System.currentTimeMillis();
					Customer _customer=new Customer();
					this.checkService.checkCustomerFile(file, newName, _customer, bizNo);
					_customer.setActNo(act.getActNo());
					_customer.setOldName(file.getOriginalFilename());
					_customer.setNewName(newName);
					_customer.setFileStatus(Customer.CUSTOMER_STATUS_UNCHECK);
					_customer=this.customerService.saveOrUpdate(_customer);
				
		
	}
    /**
     * 批量生成客户信息
     */
    @Transactional(rollbackFor=Exception.class)
    public void genCoupons(MarketingAct act,List<Customer> customers,Long customerId) throws AppException {
    		int i=0;
			for(Customer customer: customers){
				Coupon coupon = new Coupon();
				coupon.setAcctMobile(customer.getMobile());
				coupon.setAcctName(customer.getName());
				coupon.setAcctNo(customer.getAccount());
				if(act.getBizNo().equals(Coupon.BIZ_TYPE_VOUCHER)){
					BigDecimal backAmount = act.getBackRate().multiply(customer.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
					coupon.setBackAmount(act.getMaxAmount().min(backAmount));
				}
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
                couponDao.saveOrUpdate(coupon);
                this.genCode(coupon, act);
				this.genTxtFile(coupon, act);
                i++;
			}
    }
	/**
	 * 二维码生成
	 * @param coupon
	 */
	public void genCode(Coupon coupon,MarketingAct act)throws AppException{
		String content=BarCodeFormat.genBarCodeCode(coupon, act);
		this.barCodeService.genCode(content,coupon.getCouponId().toString());
		
	}
	/**
	 * 彩信文件生成
	 * @param coupon
	 * @param marketingAct
	 */
	public void genTxtFile(Coupon coupon,MarketingAct marketingAct)throws AppException{
		String content=MmsTemplate.genCustomerContent(coupon, marketingAct);
		this.fileService.genTextFile(content, coupon.getCouponId());
	}

	@Transactional
	public void createMarketingAct(MarketingAct act, MultipartFile partnerFile)throws AppException {
		if(act.getStartDate().after(act.getEndDate())){
			throw new AppException(ErrorsCode.BIZ_STARDATE_AFTER_ENDDATE,"");
		}
		this.checkService.checkCodeCheck(act);
		Set<Partner> partners=this.checkService.partnerCheck(partnerFile,String.valueOf(System.currentTimeMillis()));
		act.setPartners(partners);
		act.setGenTime(new Date());
		act.setActStatus(MarketingAct.STATUS_WAIT_ADD_CUSTOMER);
		act.setMmsSending(MarketingAct.MMS_SENDING_NO);
		act.setSmsSending(MarketingAct.SMS_SENDING_NO);
		MarketingCatalog marketingCatalog=this.marketingCatalogDao.get(act.getMarketingCatalog().getId());
		act.setMarketingCatalog(marketingCatalog);
        actDao.save(act);
	}

	@Transactional
	public void invalidMarketingAct(Long actNo)throws AppException {
            MarketingAct act = actDao.findUniqueByProperty("actNo", actNo);
            if (act.getActStatus().equals(MarketingAct.STATUS_BEFORE_RECHECK) || act.getActStatus().equals(MarketingAct.STATUS_WAIT_ADD_CUSTOMER)) {
				act.setActStatus(MarketingAct.STAUS_DELETE);
				actDao.update(act);
			} else {
				throw new AppException(ErrorsCode.BIZ_MARKETACT_DONOT_DELETE,"do not delete");
		}
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
	public List<MarketingAct> findByCondition(MarketingActCondition mac,Integer start,Integer end) {
		return this.actDao.excuteSimpleQuery(mac,start,end);
	}

	@Override
	public MarketingActSummary marketingSummary(Long actNo) {
		return this.actDao.marketingSummary(actNo);
	}

	@Override
	public List<MarketingAct> findMarketingActEntriesByActStatus(Integer actStatus,
			Integer first, Integer max) {
		return this.actDao.findListByQuery("from MarketingAct a where a.actStatus=?", first, max, actStatus);
	}
	@Override
	public long countByActStatus(Integer actStatus){
		return this.actDao.findLong("select count(*) from MarketingAct a where a.actStatus=?", actStatus);
	}
	@Override
    public List<MarketingAct> findMarketingActEntries2Check(Integer first,Integer max){
    	return this.actDao.findListByQuery("from MarketingAct a where a.actStatus=? or a.actStatus=?", first, max, MarketingAct.STATUS_BEFORE_RECHECK,MarketingAct.STATUS_APPEND_CUSTOMER);
    }
	@Override
	public long count2Check(){
		return this.actDao.findLong("select count(*) from MarketingAct a where a.actStatus=? or a.actStatus=?", MarketingAct.STATUS_BEFORE_RECHECK,MarketingAct.STATUS_APPEND_CUSTOMER);
	}
	@Override
	public int countByCondition(MarketingActCondition mac) {
		return this.actDao.countSimpleQuery(mac);
	}
	@Transactional
	public void update(MarketingAct marketingAct,Long [] partners) throws AppException {
		MarketingAct act = this.findByActNo(marketingAct.getActNo());
		act.setActName(marketingAct.getActName());
		if(partners!=null){
			Set<Partner> partnerSet=new HashSet<Partner>();
			for(Long partnerId:partners){
				Partner partner=this.partnerService.findById(partnerId);
				partnerSet.add(partner);
			}
			act.setPartners(partnerSet);
		}	
		act.setMmsTitle(marketingAct.getMmsTitle());
		act.setStartDate(marketingAct.getStartDate());
		act.setEndDate(marketingAct.getEndDate());
		act.setTimes(marketingAct.getTimes());	
		act.setActDetail(marketingAct.getActDetail());
		act.setBindCard(marketingAct.getBindCard());
		act.setMmsTemplate(marketingAct.getMmsTemplate());
		if(marketingAct.getBindCard().equals(MarketingAct.BIND_CARD_YES)){
			act.setCheckCode(marketingAct.getCheckCode());
		}
		if(act.getBizNo().equals(Coupon.BIZ_TYPE_EXCHANGE)){
			act.setAmount(marketingAct.getAmount());
			act.setExchangeName(marketingAct.getExchangeName());
		}
		else if(act.getBizNo().equals(Coupon.BIZ_TYPE_DISCOUNT)){
			act.setRebateRate(marketingAct.getRebateRate());
		}
		else{
			act.setBackRate(marketingAct.getBackRate());
			act.setMaxAmount(marketingAct.getMaxAmount());
		}
	    this.actDao.update(act);
	}

	@Override
	public MarketingAct find2Update(Long actNo) throws AppException {
		MarketingAct marketingAct=this.actDao.findUniqueByProperty("actNo", actNo);
		if(!marketingAct.getActStatus().equals(MarketingAct.STATUS_BEFORE_RECHECK)&& !marketingAct.getActStatus().equals(MarketingAct.STATUS_WAIT_ADD_CUSTOMER)){
			throw new AppException(ErrorsCode.BIZ_MARKETINGACT_DOT_NO_UPDATE,"");
		}
		return marketingAct;
	}

	@Override
	@Transactional
	public MarketingAct submit2check(Long actNo) throws AppException {
		MarketingAct act=this.findByActNo(actNo);
		List<Customer> customers=this.customerDao.find("from Customer c where c.actNo=? and c.fileStatus=?", act.getActNo(),Customer.CUSTOMER_STATUS_UNCHECK);
		if(customers.size()==0){
			throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CUSTOMER_FILE_NULL_DO_NOT_SUBMIT_CHECK,"");
		}
		if(act.getActStatus()>=MarketingAct.STATUS_BEFORE_GIVE){
			act.setActStatus(MarketingAct.STATUS_APPEND_CUSTOMER);
		}else{
			act.setActStatus(MarketingAct.STATUS_BEFORE_RECHECK);
		}
		
		this.actDao.update(act);
		return act;
	}
	public List<Coupon> getCoupon2Send(Long actNo,Integer msType) throws AppException {
		synchronized (this) {
			MarketingAct act = this.actDao.get(actNo);
			List<Coupon> coupons=null;
			if(!act.getActStatus().equals(MarketingAct.STATUS_BEFORE_GIVE)){
				throw new AppException(ErrorsCode.BIZ_MARKETING_GIVED, "");
			}
			if (msType.equals(SendList.MS_TYPE_SMS)){
				if (act.getSmsSending().equals(MarketingAct.SMS_SENDING_YES)) {
					throw new AppException(ErrorsCode.BIZ_SMS_SENDING, "");
				}
				coupons = this.couponDao
						.findListByQuery(
								"from Coupon c where c.smsStatus <>? and c.marketingAct.actNo=?",
								0, Coupon.SINGLE_SEND_MAX, Coupon.SMS_STATUS_SNED, actNo);
				if (coupons.size() == 0) {
					throw new AppException(
							ErrorsCode.BIZ_SMS_NOT_COUPON_TO_SEND, "");
				}
			}else{
				if (act.getMmsSending().equals(MarketingAct.MMS_SENDING_YES)) {
					throw new AppException(ErrorsCode.BIZ_MMS_SENDING, "");
				}
				coupons = this.couponDao
						.findListByQuery(
								"from Coupon c where  c.mmsStatus<>? and c.marketingAct.actNo=?",
								0, Coupon.SINGLE_SEND_MAX, Coupon.MMS_STATUS_SNED, actNo);
				if(coupons.size()==0){
					throw new AppException(ErrorsCode.BIZ_MMS_NOT_COUPON_TO_SEND,"");
				}
			}
			this.lockMarketingActSendStatus(actNo, msType);
			
			return coupons;
		}
	}
	@Override
	public void sendOver(Long actNo) {
		this.actDao.excuteByHQL("update MarketingAct m set m.actStatus=? where actNo=?", MarketingAct.STATUS_AFTER_GIVE,actNo);
		
	}
	@Override
	/**
	 * 解锁发送状态
	 */
	public void unLockMarketingActSendStatus(Long actNo,Integer type) throws AppException {
		if(type.equals(SendList.MS_TYPE_MMS)){
			this.actDao.excuteByHQL(
					"update MarketingAct act set act.mmsSending=? where act.actNo=?",
					MarketingAct.MMS_SENDING_NO, actNo);
		}else{
			this.actDao.excuteByHQL(
					"update MarketingAct act set act.smsSending=? where act.actNo=?",
					MarketingAct.SMS_SENDING_NO, actNo);
		}
		
	}
	/**
	 * 锁定发送状态
	 * @param actNo
	 * @param type
	 */
	private void lockMarketingActSendStatus(Long actNo,Integer type){
		if(type.equals(SendList.MS_TYPE_MMS)){
			this.actDao.excuteByHQL(
					"update MarketingAct act set act.mmsSending=? where act.actNo=?",
					MarketingAct.MMS_SENDING_YES, actNo);
		}else{
			this.actDao.excuteByHQL(
					"update MarketingAct act set act.smsSending=? where act.actNo=?",
					MarketingAct.SMS_SENDING_YES, actNo);
		}
	}
	@Override
	public void send(Long actNo, Integer msType) throws AppException {
		MarketingAct act=this.actDao.get(actNo);
	    this.sendService.send(this.getCoupon2Send(actNo, msType), act, this.getSendInvokeService(msType), null, null);
		
	}
	@Override
	public void sendOne(Long couponId, Integer msType)throws AppException {
		Coupon coupon = this.couponDao.get(couponId);
		MarketingAct act = coupon.getMarketingAct();
		if(msType.equals(SendList.MS_TYPE_MMS)){
			if (act.getMmsSending().equals(MarketingAct.MMS_SENDING_YES)) {
				throw new AppException(ErrorsCode.BIZ_MMS_SENDING, "");
			}
		}else{
			if (act.getSmsSending().equals(MarketingAct.SMS_SENDING_YES)) {
				throw new AppException(ErrorsCode.BIZ_SMS_SENDING, "");
			}
		}
		
		this.sendService.sendOne(coupon, act, this.getSendInvokeService(msType));
		
	}
    private SendInvokeService getSendInvokeService(Integer msType){
    	if(msType.equals(SendList.MS_TYPE_MMS)){
    		return this.mmsSendInvokeService;
    	}else{
    		return this.smsSendInvokeService;
    	}
    }



}
