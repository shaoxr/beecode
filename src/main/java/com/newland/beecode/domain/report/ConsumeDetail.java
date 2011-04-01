package com.newland.beecode.domain.report;

import java.math.BigDecimal;
import java.util.Date;

public class ConsumeDetail {
	
	private Date checkDay;
	
	private String actName;
	
	private BigDecimal originalAmount;
	
	private BigDecimal cost;
	
	private BigDecimal rebateAmount;
	
    private BigDecimal rebateRate;
	
	private String bizName;
	
	
	private String parterNo;
	
	private String parterName;
	
	private String  couponId;
	
	private String  acctMobile;
	
	public Date getCheckDay() {
		return checkDay;
	}

	public void setCheckDay(Date checkDay) {
		this.checkDay = checkDay;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public BigDecimal getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getRebateAmount() {
		if(rebateAmount!=null){
			return rebateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public BigDecimal getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(BigDecimal rebateRate) {
		this.rebateRate = rebateRate;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getParterNo() {
		return parterNo;
	}

	public void setParterNo(String parterNo) {
		this.parterNo = parterNo;
	}

	public String getParterName() {
		return parterName;
	}

	public void setParterName(String parterName) {
		this.parterName = parterName;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getAcctMobile() {
		return acctMobile;
	}

	public void setAcctMobile(String acctMobile) {
		this.acctMobile = acctMobile;
	}

	
	

}
