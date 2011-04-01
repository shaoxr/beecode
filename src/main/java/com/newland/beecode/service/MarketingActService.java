package com.newland.beecode.service;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;

public interface MarketingActService {

	
	/**
	 * 审核营销活动
	 * @param actNo
	 * @param actStatus 通过或者不通过
	 */
	public long checkMarketingAct(Long actNo, Integer actStatus)throws AppException;
	
	/**
	 * 生成礼券
	 * @param act
	 */
	public long genCoupons(MarketingAct act) throws AppException;
	/**
	 * 发放礼券
	 * @param actNo
	 * @throws AppException
	 */
	public void marketingActSend(Long actNo)throws AppException;
	
	/**
	 * 添加营销活动
	 * @param act
	 * @param file
	 */
	public void createMarketingAct(MarketingAct act, MultipartFile file) throws AppException;
	
	/**
	 * 作废营销活动
	 * @param actNo
	 */
	public void invalidMarketingAct(Long actNo)throws AppException;
	
	/**
	 * 添加营销客户
	 * @param actNo
	 * @param file
	 */
	public void append(Long actNo, MultipartFile file)throws AppException;
	
	/**
	 * 活动过期处理
	 */
	public void expiredProc();	
	/**
	 * genCode
	 * @param coupon
	 * @param act
	 */
	public void genCode(Coupon coupon,MarketingAct act);
}
