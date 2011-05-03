package com.newland.beecode.service;

import java.util.List;

import com.intensoft.dao.hibernate.SimpleQueryCondition;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.CouponSumaryReport;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.service.impl.CouponBackoffRequest;
import com.newland.beecode.service.impl.CouponCheckRequest;

public interface CouponService {

	/**
	 * 检查礼券是否有效
	 * @param req
	 * @return FALSE 无效
	 * @throws AppException
	 */
	public Coupon checkCoupon(CouponCheckRequest req) throws AppException;
	
	/**
	 * 消费礼券
	 * @param req
	 */
	public Coupon consumeCoupon(CouponCheckRequest req)throws AppException;
	
	/**
	 * 活动日报表
	 * @param actNo
	 * @return
	 */
	public CouponSumaryReport summaryByMarketing(Long actNo);
	
	/**
	 * 撤消并重新生成指定用户的礼券
	 * @param couponId
	 * @param mobile
	 * @return 新礼券
	 */
	public Coupon reversalCoupon(Long couponId, String mobile);
	/**
	 * 冲正
	 * @param req
	 */
	public void backOff(CouponBackoffRequest req);
	/**
	 * 查询礼券
	 * @param req
	 * @throws AppException 
	 */
	public Coupon queryCoupon(CouponCheckRequest req) throws AppException;
	
	
	public Coupon findByCoupon(Long couponId);
	
	
	public ReportResult reportCount(ReportForm reportForm);
	
	public ReportResult reportDetail(ReportForm reportForm);
	public List<Coupon> findByCondition(SimpleQueryCondition condition,Integer start,Integer end);
	
	public int countByCondition(SimpleQueryCondition condition);
}
