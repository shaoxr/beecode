package com.newland.beecode.domain.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartnerSummaryReport {
	
	public PartnerSummaryReport() {
		dayRpts = new ArrayList<PartnerSummaryReport.DayReport>();
		marketingRpts = new ArrayList<PartnerSummaryReport.MarketingReport>();
	}

	public static class DayReport{
		/**
		 * 日期
		 */
		private Date day;
		
		/**
		 * 日统计
		 */
		private Integer dayCount;
		
		/**
		 * 业务名称
		 */
		private String bizName;

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

		public Integer getDayCount() {
			return dayCount;
		}

		public void setDayCount(Integer dayCount) {
			this.dayCount = dayCount;
		}

		public String getBizName() {
			return bizName;
		}

		public void setBizName(String bizName) {
			this.bizName = bizName;
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
	
	public static class MarketingReport{
		
		/**
		 * 活动名称
		 */
		private String actName;
		
		/**
		 * 使用次数
		 */
		private Integer usedTimes;
		
		/**
		 * 业务名称
		 */
		private String bizName;

		/**
		 * 兑换总价值
		 */
		private BigDecimal exchangeAmount;
		
		/**
		 * 折让金额
		 */
		private BigDecimal rebateAmount;

		public String getActName() {
			return actName;
		}

		public void setActName(String actName) {
			this.actName = actName;
		}

		public Integer getUsedTimes() {
			return usedTimes;
		}

		public void setUsedTimes(Integer usedTimes) {
			this.usedTimes = usedTimes;
		}

		public String getBizName() {
			return bizName;
		}

		public void setBizName(String bizName) {
			this.bizName = bizName;
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
	
	private List<DayReport> dayRpts;
	
	private List<MarketingReport> marketingRpts;
	
	private List<PartnerSummaryItem> items;

	public List<DayReport> getDayRpts() {
		return dayRpts;
	}

	public void setDayRpts(List<DayReport> dayRpts) {
		this.dayRpts = dayRpts;
	}

	public List<MarketingReport> getMarketingRpts() {
		return marketingRpts;
	}

	public void setMarketingRpts(List<MarketingReport> marketingRpts) {
		this.marketingRpts = marketingRpts;
	}

	public List<PartnerSummaryItem> getItems() {
		return items;
	}

	public void setItems(List<PartnerSummaryItem> items) {
		this.items = items;
	}
}
