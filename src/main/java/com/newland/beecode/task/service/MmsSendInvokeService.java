package com.newland.beecode.task.service;

import java.util.ArrayList;

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
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.MMSService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.task.SendParam;

/**
 * @author shaoxr:
 * @version 2011-5-30 下午09:47:12
 * 
 */
@Service(value="mmsSendInvokeService")
public class MmsSendInvokeService implements SendInvokeService{
	private  Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private MMSService mmsService; 
	@Autowired
	private MarketingActService marketingActService;
	@Autowired
	private CouponDao couponDao;
	private Integer msType=SendList.MS_TYPE_MMS;

	@Override
	public void sendRun(SendParam sp)throws Exception{
			  Coupon coupon=new Coupon();
			  /*String[] str=mmsService.sendMMS(sp);
			  logger.debug("-------send :"+str[0]);
			  if(str[0].indexOf("OK")>=0){
				coupon.setMmsStatus(Integer.valueOf(RespStatus.RESP_SUCCESS));
			  }else{
				  coupon.setMmsStatus(Integer.valueOf(RespStatus.RESP_ERROR));
				  coupon.setMmsDesc(str[0]);
			  }*/
			  String resp=this.mmsService.sendMMSByMontnets(sp);
			  if(resp.length()>10){
				  logger.debug("send success mobile:" + sp.getMobile());
				  coupon.setMmsStatus(Integer.valueOf(RespStatus.RESP_SUCCESS)); 
				  coupon.setMmsDesc(resp);
			  }else{
				  coupon.setMmsStatus(Integer.valueOf(RespStatus.RESP_ERROR));
				  coupon.setMmsDesc(resp);
				  logger.debug("send error error code:"+resp+";mobile:" + sp.getMobile());
			  }
			this.couponDao.excuteByHQL("update Coupon c set c.mmsStatus=?,c.mmsDesc=? where c.couponId=?", coupon.getMmsStatus(),coupon.getMmsDesc(),sp.getCouponId());
		
		
	}

	@Override
	public void sendOver(Long actNo, Long sendListId) {
		marketingActService.unLockMarketingActSendStatus(actNo,  msType);
		
	}

	@Override
	public Integer getMsType() {
		return this.msType;
		
	}

}
