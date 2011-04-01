package com.newland.beecode.service.impl;

/**
 * 礼券查询请求类
 * @author Jason
 *
 */
public class CouponQueryRequest {

	/**
	 * 商户号
	 */
	private String partnerNo;
	
	/**
	 * 礼券编号
	 */
	private String couponNo;
	
	/**
	 * 设备编号
	 */
	private String deviceNo;

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	
}
