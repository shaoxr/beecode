package com.newland.beecode.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.BeecodeService;
import com.newland.beecode.service.CouponService;
import com.newland.utils.NewlandUtil;

public class BeecodeServiceImpl implements BeecodeService {
	private final Log logger = LogFactory.getLog(BeecodeServiceImpl.class);
	@Autowired
	private CouponService couponService;
	@SuppressWarnings("unchecked")
	@Override
	public Map exchangeBackoff(Map reqMap) {
		try {
			CouponBackoffRequest req=new CouponBackoffRequest((String)reqMap.get(2), (String)reqMap.get(3), (String)reqMap.get(5), (String)reqMap.get(9), (String)reqMap.get(12));
		    this.couponService.backOff(req);
		    reqMap.put(7, ErrorsCode.POSP_RIGHT);
		} catch (Exception e) {
			logger.error("",e);
			reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
		}
		return reqMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map exchange(Map reqMap) {
		try {
			CouponCheckRequest req=new CouponCheckRequest();
			req.setAmount((BigDecimal)reqMap.get(1));
			req.setBatchNo((String)reqMap.get(12));
			req.setBizType((String)reqMap.get(0));
			req.setCheckCode((String)reqMap.get(11));
			req.setDeviceNo((String)reqMap.get(9));
			req.setPartnerNo((String)reqMap.get(3));
			req.setCouponString((String)reqMap.get(5));
			req.setTraceNo((String)reqMap.get(2));
			req.setTxnTime(new Date());
			Coupon coupon=this.couponService.consumeCoupon(req);
			reqMap.put(7, ErrorsCode.POSP_RIGHT);
			reqMap.put(10, coupon.getMarketingAct().getExchangeName());
		} catch (Exception e) {
			if(e instanceof AppException){
				reqMap.put(7, this.convertCode(((AppException) e).getCode()));
			}else{
				logger.error("",e);
				reqMap.put(7, ErrorsCode.POSP_ERR_SYSTEM_EEROR);
			}
		}
		return reqMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map discount(Map reqMap) {
		try {
			CouponCheckRequest req=new CouponCheckRequest();
			req.setOriginalAmount((BigDecimal)reqMap.get(1));
			req.setOffAmount((BigDecimal)reqMap.get(4));
			req.setBatchNo((String)reqMap.get(12));
			req.setBizType((String)reqMap.get(0));
			req.setCheckCode((String)reqMap.get(11));
			req.setDeviceNo((String)reqMap.get(9));
			req.setTraceNo((String)reqMap.get(2));
			req.setPartnerNo((String)reqMap.get(3));
			req.setRebateRate((BigDecimal)reqMap.get(8));
			req.setCouponString((String)reqMap.get(5));
			req.setTxnTime(new Date());
			Coupon coupon=this.couponService.consumeCoupon(req);
			reqMap.put(7,ErrorsCode.POSP_RIGHT);
			reqMap.put(10, coupon.getMarketingAct().getActName());
		} catch (Exception e) {
			if(e instanceof AppException){
				reqMap.put(7,this.convertCode(((AppException) e).getCode()));
			}else{
				logger.error("",e);
				reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
			}
		}
		return reqMap;
	}
	@Override
	public Map voucherBackoff(Map reqMap) {
		try {
			CouponBackoffRequest req=new CouponBackoffRequest((String)reqMap.get(2), (String)reqMap.get(3), (String)reqMap.get(5), (String)reqMap.get(9), (String)reqMap.get(12));
		    this.couponService.backOff(req);
		    reqMap.put(7, ErrorsCode.POSP_RIGHT);
		} catch (Exception e) {
			logger.error("",e);
			reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
		}
		return reqMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map discountBackout(Map reqMap) {
		try {
			CouponBackoffRequest req=new CouponBackoffRequest((String)reqMap.get(2), (String)reqMap.get(3), (String)reqMap.get(5), (String)reqMap.get(9), (String)reqMap.get(12));
			this.couponService.backOff(req);
			reqMap.put(7, ErrorsCode.POSP_RIGHT);
		} catch (Exception e) {
			logger.error("",e);
			reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
		}
		return reqMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map query(Map reqMap) {
		try {
			CouponCheckRequest req=new CouponCheckRequest();
			req.setCouponString((String)reqMap.get(5));
			req.setDeviceNo((String) reqMap.get(9));
			req.setPartnerNo((String) reqMap.get(3));
			Coupon coupon=this.couponService.queryCoupon(req);
			if(coupon.getBusinessType().equals(Coupon.BIZ_TYPE_DISCOUNT)){
				reqMap.put(14, Coupon.BIZ_TYPE_DISCOUNT);
				reqMap.put(8,coupon.getRebateRate());
			}else if(coupon.getBusinessType().equals(Coupon.BIZ_TYPE_VOUCHER)){
				reqMap.put(14, Coupon.BIZ_TYPE_VOUCHER);
				reqMap.put(13, coupon.getBackAmount());
			}else{
				reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
			}
			reqMap.put(7, ErrorsCode.POSP_RIGHT);
			
		} catch (Exception e) {
			if(e instanceof AppException){
				reqMap.put(7,this.convertCode(((AppException) e).getCode()));
			}else{
				logger.error("",e);
				reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
			}
		}
		return reqMap;
	}

	@Override
	public Map voucher(Map reqMap) {
		try {
			CouponCheckRequest req=new CouponCheckRequest();
			req.setOriginalAmount((BigDecimal)reqMap.get(1));
			req.setBackAmount((BigDecimal)reqMap.get(13));
			req.setBatchNo((String)reqMap.get(12));
			req.setBizType((String)reqMap.get(0));
			req.setCheckCode((String)reqMap.get(11));
			req.setDeviceNo((String)reqMap.get(9));
			req.setTraceNo((String)reqMap.get(2));
			req.setPartnerNo((String)reqMap.get(3));
			req.setCouponString((String)reqMap.get(5));
			req.setTxnTime(new Date());
			Coupon coupon=this.couponService.consumeCoupon(req);
			reqMap.put(7,ErrorsCode.POSP_RIGHT);
			reqMap.put(10, coupon.getMarketingAct().getActName());
		} catch (Exception e) {
			if(e instanceof AppException){
				reqMap.put(7,this.convertCode(((AppException) e).getCode()));
			}else{
				logger.error("",e);
				reqMap.put(7,ErrorsCode.POSP_ERR_SYSTEM_EEROR);
			}
		}
		return reqMap;
	}
	private String convertCode(String code){
		if(code.equals(ErrorsCode.ERR_COUPON_EXPIRED)){
			return ErrorsCode.POSP_ERR_COUPON_EXPIRED;
		}
		if(code.equals(ErrorsCode.ERR_COUPON_INVALID)){
			return ErrorsCode.POSP_ERR_COUPON_INVALID;
		}
		if(code.equals(ErrorsCode.ERR_COUPON_NOT_EXIST)){
			return ErrorsCode.POSP_ERR_COUPON_NOT_EXIST;
		}
		if(code.equals(ErrorsCode.ERR_COUPON_PARTNER_NOT_FOUND)){
			return ErrorsCode.POSP_ERR_COUPON_PARTNER_NOT_FOUND;
		}
		if(code.equals(ErrorsCode.ERR_COUPON_CARD_NOT_USE)){
			return ErrorsCode.POSP_ERR_COUPON_CARD_DO_NOT_USE;
		}
		
		
		return ErrorsCode.POSP_ERR_SYSTEM_EEROR;
	}

	
}
