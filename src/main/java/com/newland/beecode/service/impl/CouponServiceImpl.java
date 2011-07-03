package com.newland.beecode.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.intensoft.dao.hibernate.SimpleQueryCondition;
import com.newland.beecode.dao.CouponCtrlDao;
import com.newland.beecode.dao.CouponDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.CouponCtrl;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.MsStatus;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.exception.SuccessCode;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.MarketingActService;
import com.newland.utils.UuidHelper;
import javax.annotation.Resource;

@Service("couponService")
public class CouponServiceImpl implements CouponService {

    @Resource(name = "couponDao")
    private CouponDao couponDao;
    
    @Resource(name = "couponCtlDao")
    private CouponCtrlDao ctrlDao;
    
    @Autowired
    private MarketingActService marketingActService;
    
	@Autowired
	private FileService fileService;
	
	@Autowired
	private MessageSource messageSource;

    @Override
    /**
     * 基本信息检查    
     */
    public Coupon checkCoupon(CouponCheckRequest req) throws AppException {
        Coupon coupon = null;

        if (req.getCouponId() != null) {
            coupon = couponDao.findUniqueByProperty("couponId", req.getCouponId());
        } else if (req.getSerialNo() != null) {
            coupon = couponDao.findUniqueByProperty("serialNo", req.getSerialNo());
        }
        if (coupon == null) {
            throw new AppException(ErrorsCode.ERR_COUPON_NOT_EXIST,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] not exist.");
        }
     // 检查礼券状态是否无效
        if (coupon.getCouponStatus().equals(Coupon.STATUS_VALID) == false) {
            throw new AppException(ErrorsCode.ERR_COUPON_INVALID,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] expired.");
        }
        if (coupon.getMarketingAct().getEndDate().getTime() < new Date().getTime()
                || coupon.getMarketingAct().getStartDate().getTime() > new Date().getTime()) {
            throw new AppException(ErrorsCode.ERR_COUPON_EXPIRED,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] expired.");
        }

        // 检查活动状态过期或关闭
        if (coupon.getMarketingAct().getActStatus().equals(MarketingAct.STATUS_CLOSED) == true) {
            throw new AppException(ErrorsCode.ERR_COUPON_INVALID,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] invalid.");
        }

        boolean hasPartner = false;
        for (Iterator<Partner> it = coupon.getMarketingAct().getPartners().iterator(); it.hasNext();) {
            Partner partner = it.next();
            if (partner.getPartnerNo().equals(req.getPartnerNo())) {
                hasPartner = true;
                break;
            }
        }
        if (hasPartner == false) {
            throw new AppException(ErrorsCode.ERR_COUPON_PARTNER_NOT_FOUND,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "],partner=["
                    + req.getPartnerNo() + "] partner not found.");
        }
        

        return coupon;
    }
    /**
     * 相信信息检查    
     * @param req
     * @param coupon
     * @throws AppException
     */
    private void infoCheck(CouponCheckRequest req,Coupon coupon)throws AppException{
    	if (!coupon.getBusinessType().equals(Coupon.BIZ_TYPE_EXCHANGE)) {
    		if(coupon.getBusinessType().equals(Coupon.BIZ_TYPE_VOUCHER)){
    			if(coupon.getBackAmount().compareTo(req.getBackAmount())<0){
    				throw new AppException(ErrorsCode.ERR_COUPON_INVALID,
                            "Request id=[" + req.getCouponId() + "],serialNo=["
                            + req.getSerialNo() + "] expired.");
    			}
    		}
    		if(coupon.getBusinessType().equals(Coupon.BIZ_TYPE_DISCOUNT)){
    			if(coupon.getRebateRate().compareTo(req.getRebateRate())!=0 ||
    					coupon.getMarketingAct().getMaxAmount().compareTo(req.getOriginalAmount().subtract(req.getOffAmount()))<0){
        			throw new AppException(ErrorsCode.ERR_COUPON_INVALID,
                            "Request id=[" + req.getCouponId() + "],serialNo=["
                            + req.getSerialNo() + "] expired.");
        		}
    		}
    		
			if (!coupon.getAcctNo().equals(Coupon.DEFAULT_CARD)) {
					if (!coupon.getAcctNo().equals(req.getCheckCode())) {
						throw new AppException(
								ErrorsCode.ERR_COUPON_CARD_NOT_USE,
								"Request id=[" + req.getCouponId()
										+ "],serialNo=[" + req.getSerialNo()
										+ "] ,cardNo=[" + req.getCheckCode()
										+ "] NOT USE.");
					}
			} else {
				MarketingAct act = coupon.getMarketingAct();
				if (act.getBindCard().equals(MarketingAct.BIND_CARD_YES)) {
					boolean pass=false;
					String[] strs=act.getCheckCode().split(MarketingAct.NEW_LINE);
					for(String str:strs){
					    str=str.replace("*", "").trim();
					    if(req.getCheckCode().indexOf(str)==0){
					    	pass=true;
					    }
					}
					if(!pass){
						throw new AppException(
								ErrorsCode.ERR_COUPON_CARD_NOT_USE,
								"Request id=[" + req.getCouponId()
										+ "],serialNo=[" + req.getSerialNo()
										+ "] ,cardNo=[" + req.getCheckCode()
										+ "] NOT USE.");
					}

				}
			}
		}
		
		
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized Coupon consumeCoupon(CouponCheckRequest req) throws AppException {
        Coupon coupon=checkCoupon(req);
        infoCheck(req,coupon);

        CouponCtrl ctrl = new CouponCtrl();
        ctrl.setPartnerNo(req.getPartnerNo());
        ctrl.setCouponId(coupon.getCouponId());
        ctrl.setSerialNo(coupon.getSerialNo());
        ctrl.setTraceNo(req.getTraceNo());
        ctrl.setDeviceNo(req.getDeviceNo());
        ctrl.setCheckDate(new Date());
        ctrl.setCheckDay(new Date());
        ctrl.setRebateRate(req.getRebateRate());
        ctrl.setBusinessType(req.getBizType());
        ctrl.setEncodeVersion("2");
        if(coupon.getBusinessType().equals(Coupon.BIZ_TYPE_EXCHANGE)){
        	ctrl.setAmount(coupon.getMarketingAct().getAmount());
        }else{
        	if(coupon.getBusinessType().equals(Coupon.BIZ_TYPE_VOUCHER)){
        		ctrl.setBackAmount(req.getBackAmount().min(req.getOriginalAmount()));
        	}
        	ctrl.setAmount(req.getAmount());
        }
        ctrl.setOriginalAmount(req.getOriginalAmount());
        ctrl.setOffAmount(req.getOffAmount());
        ctrl.setBatchNo(req.getBatchNo());
        ctrl.setVoidFlag(CouponCtrl.VOID_FLAG_NORMAL);
        ctrlDao.save(ctrl);

        coupon.setRemainTimes(coupon.getRemainTimes() - 1);
        if (coupon.getRemainTimes() <= 0) {
            coupon.setCouponStatus(Coupon.STATUS_FINISH);
        }
        couponDao.update(coupon);
        return coupon;

    }

    @Override
    @Transactional
    public Coupon reversalCoupon(Long couponId, String mobile) throws AppException{
        Coupon coupon = couponDao.findUniqueByProperty("couponId", couponId);
        coupon.setCouponStatus(Coupon.STATUS_LOST);
        
        couponDao.update(coupon);
        
        coupon = giveCoupons(coupon, mobile, coupon.getMarketingAct());
        this.marketingActService.genCode(coupon, coupon.getMarketingAct());
        this.marketingActService.genTxtFile(coupon, coupon.getMarketingAct());
        return coupon;
    }

    /**
     * 生成指定用户礼券
     * 
     * @param cp
     * @param mobile
     * @param act
     * @return
     */
    private Coupon giveCoupons(Coupon cp, String mobile, MarketingAct act) {
        Coupon coupon = new Coupon();
        coupon.setAcctNo(cp.getAcctNo());
        if (mobile == null) {
            coupon.setAcctMobile(cp.getAcctMobile());
        } else {
            coupon.setAcctMobile(mobile);
        }
        if(cp.getBusinessType().equals(Coupon.BIZ_TYPE_VOUCHER)&&cp.getBackAmount()!=null){
        	coupon.setBackAmount(cp.getBackAmount());
        }
        coupon.setCheckCode(cp.getCheckCode());
        coupon.setMarketingAct(act);
        coupon.setCouponStatus(Coupon.STATUS_VALID);
        coupon.setSerialNo(UuidHelper.generateUUID());
        coupon.setBusinessType(act.getBizNo());
        coupon.setRebateRate(act.getRebateRate());
        coupon.setTimes(act.getTimes());
        coupon.setRemainTimes(cp.getRemainTimes());
        coupon.setGenTime(new Date());
        
        coupon.setAcctName(cp.getAcctName());
        coupon.setMmsStatus(0);
        coupon.setSmsStatus(0);
        couponDao.save(coupon);
        return coupon;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void backOff(CouponBackoffRequest req) throws AppException{
        Coupon coupon  = couponDao.findBySerialNo(req.getSerialNo());
        List<CouponCtrl> list = ctrlDao.findBySerialNo(req.getSerialNo());
        
        List<CouponCtrl> ctrls = ctrlDao.find("from CouponCtrl o where o.serialNo=? and o.batchNo=? and o.deviceNo=? and o.partnerNo=? and o.traceNo=? and o.voidFlag=? ", 
        		req.getSerialNo(),req.getBatchNo(),req.getDeviceNo(),req.getPartnerNo(),req.getTraceNo(),CouponCtrl.VOID_FLAG_NORMAL);
        
        /*System.out.println(list.size()+">>>>>>>>");
        for (CouponCtrl coupontCtrl : list) {
        	System.out.println(coupontCtrl.getBatchNo()+":"+req.getBatchNo()+coupontCtrl.getBatchNo().equals(req.getBatchNo()));
        	System.out.println(coupontCtrl.getDeviceNo()+":"+req.getDeviceNo()+coupontCtrl.getDeviceNo().equals(req.getDeviceNo()));
        	System.out.println(coupontCtrl.getPartnerNo()+":"+req.getPartnerNo()+coupontCtrl.getPartnerNo().equals(req.getPartnerNo()));
        	System.out.println(coupontCtrl.getTraceNo()+":"+req.getTraceNo()+coupontCtrl.getTraceNo().equals(req.getTraceNo()));
        	System.out.println(coupontCtrl.getVoidFlag().equals(CouponCtrl.VOID_FLAG_NORMAL));
            if (coupontCtrl.getBatchNo().equals(req.getBatchNo())
                    && coupontCtrl.getDeviceNo().equals(req.getDeviceNo())
                    && coupontCtrl.getPartnerNo().equals(req.getPartnerNo())
                    && coupontCtrl.getTraceNo().equals(req.getTraceNo())
                    && coupontCtrl.getVoidFlag().equals(CouponCtrl.VOID_FLAG_NORMAL)) {
                ctrl = coupontCtrl;
                System.out.println("here >..........");
            }
        }*/
        
        if (coupon != null && ctrls.size()>0) {
        	CouponCtrl ctrl=ctrls.get(0);
            ctrl.setVoidFlag(CouponCtrl.VOID_FLAG_BACKOFF);

            ctrlDao.update(ctrl);
            coupon.setRemainTimes(coupon.getRemainTimes() + 1);
            coupon.setCouponStatus(Coupon.STATUS_VALID);
            
            couponDao.update(coupon);
        }

    }

    @Override
    @Transactional
    public Coupon queryCoupon(CouponCheckRequest req) throws AppException {
        return this.checkCoupon(req);

    }

	@Override
	public Coupon findByCoupon(Long couponId) {
		return this.couponDao.findUniqueByProperty("couponId", couponId);
	}

	public List<Coupon> findByCondition(SimpleQueryCondition condition,Integer start,Integer end){
		return this.couponDao.excuteSimpleQuery(condition, start, end);
	}

	@Override
	public ReportResult reportCount(ReportForm reportForm)throws AppException {
		if(reportForm.getStartDate().after(reportForm.getEndDate())){
			throw new AppException(ErrorsCode.BIZ_STARDATE_AFTER_ENDDATE,"");
		}
		return this.couponDao.reportCount(reportForm);
	}

	@Override
	public ReportResult reportDetail(ReportForm reportForm)throws AppException{
		if(reportForm.getStartDate().after(reportForm.getEndDate())){
			throw new AppException(ErrorsCode.BIZ_STARDATE_AFTER_ENDDATE,"");
		}
		return this.couponDao.reportDetail(reportForm);
	}

	@Override
	public int countByCondition(SimpleQueryCondition condition) {
		return this.couponDao.countSimpleQuery(condition);
	}

	@Override
	public void save(Coupon coupon) {
		this.couponDao.save(coupon);
		
	}

	@Override
	public MsStatus findMSStatus(Long actNo) {
		MsStatus msStatus=new MsStatus();
		String sql="select count(t.mmssuccess),count(t.smssuccess),count(*) from " +
				" ( select case c.mms_status when 1 then c.mms_status else null end as mmssuccess, case c.sms_status when 1 then c.sms_status else null end as smssuccess from coupon c where c.marketing_act="+actNo+" ) as t";
		List<Object[]> list = this.couponDao.findBySql(sql);
		if (!list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			msStatus.setMMSSuccessCount(((BigInteger)obj[0]).longValue());
			msStatus.setSMSSuccessCount(((BigInteger)obj[1]).longValue());
			msStatus.setTotalCount(((BigInteger)obj[2]).longValue());
			return msStatus;
		}
		return msStatus;
	}

	@Override
	public List<Coupon> find2SmsSendByLimit(Long actNo,Integer start,Integer end) throws AppException {
		return this.couponDao.findListByQuery("from Coupon c where c.marketingAct.actNo=? and c.smsStatus <>? ", start, end , actNo,Coupon.SMS_STATUS_SNED);
	}
	
	@Override
	@Transactional
	public String updateRespStatus(MultipartFile file, Integer type)
			throws AppException {
		MarketingAct act=new MarketingAct();
		List<Coupon> coupons=this.fileService.getCouponsByRespStatus(act, file, type);
		String message=null;
			if(type.equals(SendList.MS_TYPE_MMS)){
				for(Coupon coupon:coupons){
				   this.updateMmsStatus(coupon);
				}
			  message= this.messageSource.getMessage(SuccessCode.UPDATE_MMS_RESP_SUCCESS, new Object[]{coupons.size()}, Locale.CHINA);
			}else{
				for(Coupon coupon:coupons){
				    this.updateSmsStatus(coupon);
				}
			  message= this.messageSource.getMessage(SuccessCode.UPDATE_SMS_RESP_SUCCESS, new Object[]{coupons.size()}, Locale.CHINA);
			}
			this.marketingActService.unLockMarketingActSendStatus(act.getActNo(), type);
			return message;
	}
	public void updateMmsStatus(Coupon coupon){
			Coupon newCoupon=this.couponDao.get(coupon.getCouponId());
			newCoupon.setMmsStatus(coupon.getMmsStatus());
			newCoupon.setMmsDesc(coupon.getMmsDesc());
			this.couponDao.update(newCoupon);
	}
	
	public void updateSmsStatus(Coupon coupon){
			Coupon newCoupon=this.couponDao.get(coupon.getCouponId());
			newCoupon.setSmsStatus(coupon.getSmsStatus());
			newCoupon.setSmsDesc(coupon.getSmsDesc());
			this.couponDao.update(newCoupon);

	}
	@Override
	public MsStatus findSendCount() {
		MsStatus msStatus=new MsStatus();
		String sql="select count(t.mmssuccess),count(t.smssuccess),count(*) from " +
				" ( select case c.mms_status when 1 then c.mms_status else null end as mmssuccess, case c.sms_status when 1 then c.sms_status else null end as smssuccess from coupon c ) as t";
		List<Object[]> list = this.couponDao.findBySql(sql);
		if (!list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			msStatus.setMMSSuccessCount(((BigInteger)obj[0]).longValue());
			msStatus.setSMSSuccessCount(((BigInteger)obj[1]).longValue());
			msStatus.setTotalCount(((BigInteger)obj[2]).longValue());
			return msStatus;
		}
		return msStatus;
	}

}
