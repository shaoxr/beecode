package com.newland.beecode.domain.report;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

/**
 * 礼券商户日摘要
 * @author Jason
 *
 */
public class CouponSummaryItem {
	
	/**
	 * 商户
	 */
	private String partnerName;

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

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

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
