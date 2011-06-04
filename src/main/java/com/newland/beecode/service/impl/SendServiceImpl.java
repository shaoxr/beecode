package com.newland.beecode.service.impl;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.SendService;
import com.newland.beecode.task.SendParam;
import com.newland.beecode.task.Task;
import com.newland.beecode.task.ThreadPoolFactory;
import com.newland.beecode.task.service.SendInvokeService;

@Service(value="sendService")
public class SendServiceImpl implements SendService {

	private Log logger = LogFactory.getLog(SendServiceImpl.class);
	
	@Autowired
	private FileService fileService;
	
	@Override
	public  void send(List<Coupon> couponList,final MarketingAct act,final SendInvokeService sendInvokeService,final Long sendListId,String dir) throws AppException {
		
		
		try {		
			final long time=System.currentTimeMillis();
			final ThreadPoolExecutor threadPool =ThreadPoolFactory.newThreadPool(couponList.size());
			if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_MMS)){
				for(Coupon coupon:couponList){
					SendParam sp=new SendParam();
					sp.setContent(this.fileService.getZIPByte(coupon.getCouponId(),dir));
					sp.setTitle(act.getMmsTitle());
					sp.setSendListId(sendListId);
					sp.setMobile(coupon.getAcctMobile());
					sp.setCouponId(coupon.getCouponId());
					threadPool.execute(new Task(sendInvokeService,sp));
				}
			}else if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_SMS)){
				for(Coupon coupon:couponList){
					SendParam sp=new SendParam();
					sp.setSmsContent(this.fileService.getTextContent(coupon.getCouponId().toString(),dir));
					sp.setTitle(act.getMmsTitle());
					sp.setMobile(coupon.getAcctMobile());
					sp.setSendListId(sendListId);
					sp.setCouponId(coupon.getCouponId());
					threadPool.execute(new Task(sendInvokeService,sp));
				}
			}
			
			threadPool.shutdown();
			new Thread(){
				public void run() {
					try {
						if(threadPool.awaitTermination(4, TimeUnit.HOURS )){
							logger.debug("sended ........,total times:"+(System.currentTimeMillis()-time));
							sendInvokeService.sendOver(act.getActNo(),sendListId);
						}
					} catch (Exception e) {
						logger.error("", e);
					}
				}
			}.start();
		} catch (Exception e) {
			if(e instanceof AppException){
				throw (AppException)e;
			}
			logger.error("", e);
		}
	
	}
	
	public void sendOne(Coupon coupon,MarketingAct act, SendInvokeService sendInvokeService) throws AppException{
		SendParam sp=new SendParam();
		if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_MMS)){
			sp.setContent(this.fileService.getZIPByte(coupon.getCouponId()));
			sp.setTitle(act.getMmsTitle());
			sp.setMobile(coupon.getAcctMobile());
			sp.setCouponId(coupon.getCouponId());
		}else if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_SMS)){
			sp.setSmsContent(this.fileService.getTextContent(coupon.getCouponId().toString()));
			sp.setTitle(act.getMmsTitle());
			sp.setMobile(coupon.getAcctMobile());
			sp.setCouponId(coupon.getCouponId());
		}
		try {
			sendInvokeService.sendRun(sp);
		} catch (Exception e) {
			throw new AppException(ErrorsCode.BIZ_MS_SEND_ERROR,"",e);
		}
	}




}
