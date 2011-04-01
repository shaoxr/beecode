package com.newland.beecode.service.impl;

/**
 * 礼券冲正请求类
 * @author Jason
 *
 */
public class CouponBackoffRequest {
	/**
	 * 
	 * @param traceNo 终端流水号
	 * @param partnerNo 商户编号
	 * @param serialNo 序列号或编号
	 * @param deviceNo 终端号
	 * @param batchNo 批次号
	 */
	public CouponBackoffRequest(String traceNo,String partnerNo,String serialNo,String deviceNo,String batchNo){
		this.batchNo=batchNo;
		this.serialNo=serialNo;
		this.deviceNo=deviceNo;
		this.partnerNo=partnerNo;
		this.traceNo=traceNo;
	}
	public CouponBackoffRequest(){
		
	}

	/**
	 * 终端流水号
	 */
	private String traceNo;
	
	/**
	 * 商户编号
	 */
	private String partnerNo;
	
	/**
	 * 序列号或编号
	 */
	private String serialNo;
	
	/**
	 * 终端号
	 */
	private String deviceNo;
	
	/**
	 * 批次号
	 */
	private String batchNo;

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
