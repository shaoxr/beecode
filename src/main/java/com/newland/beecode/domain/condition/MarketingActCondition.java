package com.newland.beecode.domain.condition;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 营销活动查询条件类
 * @author Jason
 *
 */
public class MarketingActCondition extends BaseCondition{

	private Long actNo;
	
	private String actName;
	
	@DateTimeFormat(style="M-")
	private Date startGenDate;
	
	@DateTimeFormat(style="M-")
	private Date endGenDate;
	
	@DateTimeFormat(style="M-")
	private Date startEndDate;
	
	@DateTimeFormat(style="M-")
	private Date endEndDate;
	
	private String bizNo;
	
	private Integer actStatus;
	
	private Long marketingCatalogId;

	public Long getMarketingCatalogId() {
		if(GlobalConstant.ALL_LONG.equals(this.marketingCatalogId)){
			return null;
		}
		return marketingCatalogId;
	}

	public void setMarketingCatalogId(Long marketingCatalogId) {
		this.marketingCatalogId = marketingCatalogId;
	}

	public Integer getActStatus() {
		if(GlobalConstant.ALL.equals(this.actStatus)){
			return null;
		}
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}

	public Long getActNo() {
		return actNo;
	}

	public void setActNo(Long actNo) {
		this.actNo = actNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Date getStartGenDate() {
		return startGenDate;
	}

	public void setStartGenDate(Date startGenDate) {
		this.startGenDate = startGenDate;
	}

	public Date getEndGenDate() {
		return endGenDate;
	}

	public void setEndGenDate(Date endGenDate) {
		this.endGenDate = endGenDate;
	}

	public Date getStartEndDate() {
		return startEndDate;
	}

	public void setStartEndDate(Date startEndDate) {
		this.startEndDate = startEndDate;
	}

	public Date getEndEndDate() {
		return endEndDate;
	}

	public void setEndEndDate(Date endEndDate) {
		this.endEndDate = endEndDate;
	}

	public String getBizNo() {
		if(GlobalConstant.ALL_STRING.equals(this.bizNo)){
			return null;
		}
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
}
