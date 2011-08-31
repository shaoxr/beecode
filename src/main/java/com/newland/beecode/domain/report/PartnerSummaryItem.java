package com.newland.beecode.domain.report;

import java.math.BigDecimal;
import java.util.Date;


public class PartnerSummaryItem {
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 商户名称
	 */
	private String partnerName;
	
	private String partnerNo;
	
	private String terminalName;
	
	private String terminalNo;
	
	/**
	 * 活动名称
	 */
	private String actName;
	
	/***
	 * 日期
	 */
	private Date checkDay;
	
	/**
	 * 业务名称
	 */
	private String bizName;
	/**
	 * 业务代码
	 */
	private String bizNo;
	

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
	
	private BigDecimal originalAmount;
	
	private BigDecimal backAmount;
	
	public BigDecimal getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(BigDecimal backAmount) {
		this.backAmount = backAmount;
	}

	public BigDecimal getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCheckDay() {
		return checkDay;
	}

	public void setCheckDay(Date checkDay) {
		this.checkDay = checkDay;
	}

	public BigDecimal getExchangeAmount() {
		if(exchangeAmount!=null && exchangeAmount.compareTo(new BigDecimal(0))==0)
			return null;
		return exchangeAmount;
	}

	public void setExchangeAmount(BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	public BigDecimal getRebateAmount() {
		if(rebateAmount==null)
			return rebateAmount;
		return rebateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public String getBizName() {
		return this.bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
}
