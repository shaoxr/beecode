package com.newland.beecode.task.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.CouponDao;
import com.newland.beecode.dao.RespStatusDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.SMSService;
import com.newland.beecode.task.SendParam;

/**
 * @author shaoxr:
 * @version 2011-5-30 下午09:56:30
 * 
 */
@Service(value="smsSendInvokeService")
public class SmsSendInvokeService implements SendInvokeService{
	private  Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private SMSService smsService;
	@Autowired
	private MarketingActService marketingActService;
	@Autowired
	private CouponDao couponDao;
	
	private Integer msType=SendList.MS_TYPE_SMS;
	@Override
	public void sendRun(SendParam sp) throws Exception{
		
			Coupon coupon=new Coupon();
			/*int resp = smsService.sendSMS(null, new String[] {sp.getMobile() }, sp.getSmsContent(), "",
					"gbk", 5, sp.getCouponId());
			if (resp == 0) {
				coupon.setSmsStatus(Integer.valueOf(RespStatus.RESP_SUCCESS));
				coupon.setSmsDesc("success");
			} else {
				logger.debug("error code:"+resp);
				coupon.setSmsStatus(Integer.valueOf(RespStatus.RESP_ERROR));
				coupon.setSmsDesc("error code:"+resp);
			}*/
			String resp=this.smsService.sendSMSByMontnets(sp);
			if(resp.length()>10){
				logger.debug("send success mobile:" + sp.getMobile());
				coupon.setSmsStatus(Integer.valueOf(RespStatus.RESP_SUCCESS));
				coupon.setSmsDesc(resp);
			}else{
				coupon.setSmsStatus(Integer.valueOf(RespStatus.RESP_ERROR));
				coupon.setSmsDesc(resp);
				logger.debug("send error error code:"+resp+";mobile:" + sp.getMobile());
			}
			logger.debug("-------send sms :" + sp.getMobile());
			this.couponDao.excuteByHQL("update Coupon c set c.smsStatus=?,c.smsDesc=? where c.couponId=?", coupon.getSmsStatus(),coupon.getSmsDesc(),sp.getCouponId());
		
	}
	@Override
	public void sendOver(Long actNo, Long sendListId)  {
		marketingActService.unLockMarketingActSendStatus(actNo,  msType);
		
	}
	@Override
	public Integer getMsType() {
		return this.msType;
		
	}
	

}
