package com.newland.beecode.domain.condition;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.intensoft.dao.annotation.OperatorType;
import com.intensoft.dao.annotation.SimpleQueryProperty;
import com.intensoft.dao.hibernate.SimpleQueryCondition;
import com.newland.beecode.domain.MarketingAct;

/**
 * 营销活动查询条件类
 * @author Jason
 *
 */
public class MarketingActCondition  extends SimpleQueryCondition{

	protected MarketingActCondition(Class<MarketingAct> clazz) {
		super(clazz);
		// TODO Auto-generated constructor stub
	}
	
	public MarketingActCondition(){
		super(MarketingAct.class);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "marketingCatalog.id")
	public Long getMarketingCatalogId() {
		if(GlobalConstant.ALL_LONG.equals(this.marketingCatalogId)){
			return null;
		}
		return marketingCatalogId;
	}

	public void setMarketingCatalogId(Long marketingCatalogId) {
		this.marketingCatalogId = marketingCatalogId;
	}
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "actStatus")
	public Integer getActStatus() {
		if(GlobalConstant.ALL.equals(this.actStatus)){
			return null;
		}
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "actNo")
	public Long getActNo() {
		return actNo;
	}

	public void setActNo(Long actNo) {
		this.actNo = actNo;
	}
	@SimpleQueryProperty(operator = OperatorType.LIKE,property = "actName")
	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	@SimpleQueryProperty(operator = OperatorType.GE,property = "genTime")
	public Date getStartGenDate() {
		return startGenDate;
	}

	public void setStartGenDate(Date startGenDate) {
		this.startGenDate = startGenDate;
	}
	@SimpleQueryProperty(operator = OperatorType.LE,property = "genTime")
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
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "bizNo")
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
