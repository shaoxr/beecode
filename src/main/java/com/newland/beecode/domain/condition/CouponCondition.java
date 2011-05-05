package com.newland.beecode.domain.condition;

import java.util.Date;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.intensoft.dao.annotation.OperatorType;
import com.intensoft.dao.annotation.SimpleQueryProperty;
import com.intensoft.dao.hibernate.SimpleQueryCondition;
import com.newland.beecode.domain.Coupon;

public class CouponCondition extends SimpleQueryCondition{
	
	protected CouponCondition(Class<Coupon> clazz) {
		super(clazz);
	}
    public CouponCondition(){
    	super(Coupon.class);
    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date minGenTime;

	private Date maxGenTime;
	
    private String mobile;
	
	private Long couponId;
	
	private Integer couponStatus;
	
	private Long actNo;
	
	private Long marketingCatalogId;
	
	private Integer mmsStatus;
	
	private Integer smsStatus;
	
	
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "mmsStatus")
	public Integer getMmsStatus() {
		if(GlobalConstant.ALL.equals(this.mmsStatus)){
			return null;
		}
		return mmsStatus;
	}

	public void setMmsStatus(Integer mmsStatus) {
		this.mmsStatus = mmsStatus;
	}
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "smsStatus")
	public Integer getSmsStatus() {
		if(GlobalConstant.ALL.equals(this.smsStatus)){
			return null;
		}
		return smsStatus;
	}

	public void setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
	}
	@SimpleQueryProperty(operator = OperatorType.GE,property = "minGenTime")
	public Date getMinGenTime() {
		return minGenTime;
	}

	public void setMinGenTime(Date minGenTime) {
		this.minGenTime = minGenTime;
	}
	@SimpleQueryProperty(operator = OperatorType.LE,property = "maxGenTime")
	public Date getMaxGenTime() {
		return maxGenTime;
	}

	public void setMaxGenTime(Date maxGenTime) {
		this.maxGenTime = maxGenTime;
	}
	@SimpleQueryProperty(operator = OperatorType.LIKE,property = "acctMobile")
	public String getMobile() {
		if(mobile!=null &&mobile.trim()!=""){
			return mobile;
		}
		return null;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@SimpleQueryProperty(operator = OperatorType.LIKE,property = "couponId")
	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "couponStatus")
	public Integer getCouponStatus() {
		if(GlobalConstant.ALL.equals(this.couponStatus)){
			return null;
		}
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "marketingAct.actNo")
	public Long getActNo() {
		if(GlobalConstant.ALL_LONG.equals(this.actNo)){
			return null;
		}
		return actNo;
	}

	public void setActNo(Long acctNo) {
		this.actNo = acctNo;
	}
	@SimpleQueryProperty(operator = OperatorType.EQ,property = "marketingAct.marketingCatalog.id")
	public Long getMarketingCatalogId() {
		if(GlobalConstant.ALL_LONG.equals(this.marketingCatalogId)){
			return null;
		}
		return null;
	}

	public void setMarketingCatalogId(Long marketingCatalogId) {
		this.marketingCatalogId = marketingCatalogId;
	}

	

}

