package com.newland.beecode.domain.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CouponSumaryReport {

	public static class CouponSummaryDayItem {

		/**
		 * 日期
		 */
		private Date day;
		
		/**
		 * 数量
		 */
		private Integer count;
		
		/**
		 * 兑换总价值
		 */
		private BigDecimal exchangeAmount;
		
		/**
		 * 折让金额
		 */
		private BigDecimal rebateAmount;

		public Date getDay() {
			return day;
		}

		public void setDay(Date day) {
			this.day = day;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public BigDecimal getExchangeAmount() {
			return exchangeAmount;
		}

		public void setExchangeAmount(BigDecimal exchangeAmount) {
			this.exchangeAmount = exchangeAmount;
		}

		public BigDecimal getRebateAmount() {
			return rebateAmount;
		}

		public void setRebateAmount(BigDecimal rebateAmount) {
			this.rebateAmount = rebateAmount;
		}
	}
	
	public static class CouponSummaryPartnerItem {

		/**
		 * 商户
		 */
		private String partnerName;
		
		/**
		 * 数量
		 */
		private Integer count;
		
		/**
		 * 兑换总价值
		 */
		private BigDecimal exchangeAmount;
		
		/**
		 * 折让金额
		 */
		private BigDecimal rebateAmount;

		public String getPartnerName() {
			return partnerName;
		}

		public void setPartnerName(String partnerName) {
			this.partnerName = partnerName;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public BigDecimal getExchangeAmount() {
			return exchangeAmount;
		}

		public void setExchangeAmount(BigDecimal exchangeAmount) {
			this.exchangeAmount = exchangeAmount;
		}

		public BigDecimal getRebateAmount() {
			return rebateAmount;
		}

		public void setRebateAmount(BigDecimal rebateAmount) {
			this.rebateAmount = rebateAmount;
		}
	}
	/**
	 * 按日期摘要
	 */
	private List<CouponSummaryDayItem> summaryDays;
	
	/**
	 * 按商户摘要
	 */
	private List<CouponSummaryPartnerItem> summaryPartners;
	
	/**
	 * 按商户、日期摘要
	 */
	private List<CouponSummaryItem> summary;

	public List<CouponSummaryDayItem> getSummaryDays() {
		return summaryDays;
	}

	public void setSummaryDays(List<CouponSummaryDayItem> summaryDays) {
		this.summaryDays = summaryDays;
	}

	public List<CouponSummaryPartnerItem> getSummaryPartners() {
		return summaryPartners;
	}

	public void setSummaryPartners(List<CouponSummaryPartnerItem> summaryPartners) {
		this.summaryPartners = summaryPartners;
	}

	public List<CouponSummaryItem> getSummary() {
		return summary;
	}

	public void setSummary(List<CouponSummaryItem> sumaryDaysNPartners) {
		this.summary = sumaryDaysNPartners;
	}
}
