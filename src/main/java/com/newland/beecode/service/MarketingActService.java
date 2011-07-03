package com.newland.beecode.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;

public interface MarketingActService {

	
	
	/**
	 * 添加营销活动
	 * @param act
	 * @param file
	 */
	public void createMarketingAct(MarketingAct act, MultipartFile partnerFile) throws AppException;
	
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
	public void append(Long actNo, MultipartFile file, String bizNo)throws AppException;
	/**
	 * 增加短信/彩信 内容
	 * @param act
	 */
	public void appendMmsContent(MarketingAct act)throws AppException;
	/**
	 * genCode
	 * @param coupon
	 * @param act
	 */
	public void genCode(Coupon coupon,MarketingAct act)throws AppException;
	public void genTxtFile(Coupon coupon,MarketingAct marketingAct)throws AppException;
	
	public List<MarketingAct> findByCatalog(Long id);
	
	public MarketingAct findByActNo(Long actNo);
	
	public List<MarketingAct> findByCondition(MarketingActCondition mac,Integer start,Integer end);
	
	public int countByCondition(MarketingActCondition mac);
	
	public MarketingActSummary marketingSummary(Long actNo);
	
	public List<MarketingAct> findMarketingActEntriesByActStatus(Integer actStatus,Integer first,Integer max);
	
	public long countByActStatus(Integer actStatus);
	
	public List<MarketingAct> findMarketingActEntries2Check(Integer first,Integer max);
	
	public long count2Check();
	
	
	public void update(MarketingAct marketingAct,Long [] partners) throws AppException;
	
	public MarketingAct find2Update(Long actNo)throws AppException;
	
	public MarketingAct submit2check(Long actNo)throws AppException;
	
	public void checkMarketingActByLimit(MarketingAct act,Customer customer)throws AppException;
	
	public void genCoupons(MarketingAct act,List<Customer> customers,Long customerId) throws AppException;
	
	public List<Coupon> getCoupon2Send(Long actNo,Integer msType) throws AppException;
	
	public void sendOver(Long actNo);
	
	public void unLockMarketingActSendStatus(Long actNo,Integer type);
	
	public void send(Long actNo,Integer msType)throws AppException;
	
	public void sendOne(Long couponId,Integer msType)throws AppException;
	
}
