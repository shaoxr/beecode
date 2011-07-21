package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.task.service.SendInvokeService;


public interface SendService {

	public void send(List<Coupon> couponList,MarketingAct act,SendInvokeService sendInvokeService,Long sendListId,String dir) throws AppException;
	
	public void sendOne(Coupon coupon,MarketingAct act, SendInvokeService sendInvokeService) throws AppException;
	public void sendOne(Coupon coupon,final MarketingAct act,final SendInvokeService sendInvokeService,final Long sendListId,String dir)throws AppException;
	public  void differenceSend(List<Coupon> couponList,final MarketingAct act,final Long sendListId,String dir) throws AppException;
}
