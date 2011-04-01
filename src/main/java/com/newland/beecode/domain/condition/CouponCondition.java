package com.newland.beecode.domain.condition;

import java.util.Date;

public class CouponCondition extends BaseCondition{
	
	private Date minGenTime;

	private Date maxGenTime;
	
    private String mobile;
	
	private Integer couponId;
	
	private Integer couponStatus;
	
	private Long actNo;
	
	private Long marketingCatalogId;
	
	private Integer mmsStatus;
	
	private Integer smsStatus;
	
	public Integer getMmsStatus() {
		return mmsStatus;
	}

	public void setMmsStatus(Integer mmsStatus) {
		this.mmsStatus = mmsStatus;
	}

	public Integer getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
	}

	public Date getMinGenTime() {
		return minGenTime;
	}

	public void setMinGenTime(Date minGenTime) {
		this.minGenTime = minGenTime;
	}
	
	public Date getMaxGenTime() {
		return maxGenTime;
	}

	public void setMaxGenTime(Date maxGenTime) {
		this.maxGenTime = maxGenTime;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getCouponStatus() {
		if(GlobalConstant.ALL.equals(this.couponStatus)){
			return null;
		}
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}

	public Long getActNo() {
		if(GlobalConstant.ALL_LONG.equals(this.actNo)){
			return null;
		}
		return actNo;
	}

	public void setActNo(Long acctNo) {
		this.actNo = acctNo;
	}

	public Long getMarketingCatalogId() {
		if(GlobalConstant.ALL_LONG.equals(this.marketingCatalogId)){
			return null;
		}
		return marketingCatalogId;
	}

	public void setMarketingCatalogId(Long marketingCatalogId) {
		this.marketingCatalogId = marketingCatalogId;
	}

	

}

