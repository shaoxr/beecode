package com.newland.beecode.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

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
import com.newland.beecode.service.MMSService;
import com.newland.beecode.service.SMSService;
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
	@Autowired
	private MMSService mmsService;
	@Autowired
	private SMSService smsService;
	@Resource(name="mmsFetch2SendInvokeService")
	private SendInvokeService mmsFetch2SendInvokeService;
	@Resource(name="smsFetch2SendInvokeService")
	private SendInvokeService smsFetch2SendInvokeService;
	private static String[] DIAN_XIN_MOBILE={"133","153","180","189"};
	@Override
	public  void send(List<Coupon> couponList,final MarketingAct act,final SendInvokeService sendInvokeService,final Long sendListId,String dir) throws AppException {
		
		
		try {
			//this.heartDetect(sendInvokeService.getMsType(), couponList.size());
			final long time=System.currentTimeMillis();
			final ThreadPoolExecutor threadPool =ThreadPoolFactory.newThreadPool(couponList.size());
			if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_MMS)){
				for(Coupon coupon:couponList){
					SendParam sp=new SendParam();
					//sp.setContent(this.fileService.getZIPByte(coupon.getCouponId(),dir));
					sp.setBase64Content(this.fileService.getBase64Tms(coupon.getCouponId(), dir));
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
							
						}
					} catch (Exception e) {
						logger.error("", e);
					}finally{
						sendInvokeService.sendOver(act.getActNo(),sendListId);
					}
				}
			}.start();
		} catch (Exception e) {
			sendInvokeService.sendOver(act.getActNo(),sendListId);
			if(e instanceof AppException){
				throw (AppException)e;
			}
			logger.error("", e);
		}
	
	}
	@Override
	public  void differenceSend(List<Coupon> couponList,final MarketingAct act,final Long sendListId,String dir) throws AppException {
		try {
			//this.heartDetect(sendInvokeService.getMsType(), couponList.size());
			final long time=System.currentTimeMillis();
			List<List<Coupon>> couponss=checkMobile(couponList);
			List<Coupon> DXCoupons=couponss.get(0);
			List<Coupon> UNDXCoupons=couponss.get(1);
			this.heartDetect(SendList.MS_TYPE_SMS,DXCoupons.size());
			this.heartDetect(SendList.MS_TYPE_MMS, UNDXCoupons.size());
			final ThreadPoolExecutor threadPool =ThreadPoolFactory.newThreadPool(couponList.size());
			for(Coupon coupon:DXCoupons){
				SendParam sp=new SendParam();
				sp.setSmsContent(this.fileService.getTextContent(coupon.getCouponId().toString(),dir));
				sp.setTitle(act.getMmsTitle());
				sp.setMobile(coupon.getAcctMobile());
				sp.setSendListId(sendListId);
				sp.setCouponId(coupon.getCouponId());
				threadPool.execute(new Task(this.smsFetch2SendInvokeService,sp));
			}
			for(Coupon coupon:UNDXCoupons){
				SendParam sp=new SendParam();
				//sp.setContent(this.fileService.getZIPByte(coupon.getCouponId(),dir));
				sp.setBase64Content(this.fileService.getBase64Tms(coupon.getCouponId(), dir));
				sp.setTitle(act.getMmsTitle());
				sp.setSendListId(sendListId);
				sp.setMobile(coupon.getAcctMobile());
				sp.setCouponId(coupon.getCouponId());
				threadPool.execute(new Task(this.mmsFetch2SendInvokeService,sp));
			}
			
			threadPool.shutdown();
			new Thread(){
				public void run() {
					try {
						if(threadPool.awaitTermination(4, TimeUnit.HOURS )){
							logger.debug("sended ........,total times:"+(System.currentTimeMillis()-time));
							
						}
					} catch (Exception e) {
						logger.error("", e);
					}finally{
						mmsFetch2SendInvokeService.sendOver(act.getActNo(),sendListId);
					}
				}
			}.start();
		} catch (Exception e) {
			mmsFetch2SendInvokeService.sendOver(act.getActNo(),sendListId);
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
	public void sendOne(Coupon coupon,final MarketingAct act,final SendInvokeService sendInvokeService,final Long sendListId,String dir)throws AppException{
		SendParam sp=new SendParam();
		if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_SMS)){
			
			sp.setSmsContent(this.fileService.getTextContent(coupon.getCouponId().toString(),dir));
			sp.setTitle(act.getMmsTitle());
			sp.setMobile(coupon.getAcctMobile());
			sp.setSendListId(sendListId);
			sp.setCouponId(coupon.getCouponId());
		}else if(sendInvokeService.getMsType().equals(SendList.MS_TYPE_MMS)){
			//sp.setContent(this.fileService.getZIPByte(coupon.getCouponId(),dir));
			sp.setBase64Content(this.fileService.getBase64Tms(coupon.getCouponId(), dir));
			sp.setTitle(act.getMmsTitle());
			sp.setSendListId(sendListId);
			sp.setMobile(coupon.getAcctMobile());
			sp.setCouponId(coupon.getCouponId());
		}
		try {
			sendInvokeService.sendRun(sp);
			sendInvokeService.sendOver(act.getActNo(), sendListId);
		} catch (Exception e) {
			throw new AppException(ErrorsCode.BIZ_MS_SEND_ERROR,"",e);
		}
	}
	private void heartDetect(Integer msType,long size)throws AppException{
		if(msType.equals(SendList.MS_TYPE_MMS)){
				long mmsBalance=0;
				try {
					mmsBalance = new Long(this.mmsService.getBalanceByMontnets());
				} catch (NumberFormatException e) {
					throw new AppException(ErrorsCode.BIZ_MMS_NAME_ERROR,"");
				}
				if(mmsBalance<size){
					throw new AppException(ErrorsCode.BIZ_MMS_LACK_OF_BALANCE,"");
				}
		}else{
				long smsBalance=this.smsService.getBalanceByMontnets();
				if(smsBalance<size){
					throw new AppException(ErrorsCode.BIZ_SMS_LACK_OF_BALANCE,"");
				}
		}
	}

	private List<List<Coupon>> checkMobile(List<Coupon> coupons){
		List<Coupon> DXCoupons=new ArrayList<Coupon>();
		List<Coupon> UNDXCoupons=new ArrayList<Coupon>();
		for(Coupon coupon:coupons){
			if(dianXinMobile(coupon.getAcctMobile())){
				DXCoupons.add(coupon);
			}else{
				UNDXCoupons.add(coupon);
			}
		}
		ArrayList<List<Coupon>> couponss=new ArrayList<List<Coupon>>();
		couponss.add(DXCoupons);
		couponss.add(UNDXCoupons);
		return couponss;
		
	}
	private boolean dianXinMobile(String mobile){
		boolean flag=false;
		for(String str:DIAN_XIN_MOBILE){
			if(mobile.indexOf(str)==0){
				flag=true;
			}
		}
		return flag;
	}


}
