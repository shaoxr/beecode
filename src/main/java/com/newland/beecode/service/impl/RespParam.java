package com.newland.beecode.service.impl;
/**
 * 返回
 * @author shaoxr
 *
 */
public class RespParam {
	
	public String getRespNo() {
		return respNo;
	}

	public void setRespNo(String respNo) {
		this.respNo = respNo;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
    /**
     * 返回码
     */
	private String respNo;
	/**
	 * 礼券编号
	 */
	private String couponNo;
	/**
	 * 折扣率
	 */
	private String rebate;
	/**
	 * 活动名称
	 */
	private  String actName;
	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

}
