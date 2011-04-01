package com.newland.beecode.service.impl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 礼券验证请求类
 * @author Jason
 *
 */
public class CouponCheckRequest {
	/**
	 * 礼券临时串
	 */
    private String couponString;

	/**
	 * 礼券编号
	 */
    private Long couponId;

    /**
     * 随机串号（二维码文本）
     */
    private String serialNo;
	
	/**
	 * 商户终端编号
	 */
	private String deviceNo;
	
    /**
     * 商户编号
     */
    private String partnerNo;
    
    /**
     * 折扣率
     */
    private BigDecimal rebateRate;
    
    /**
     * 业务类型
     */
    private String BizType;
    
    /**
     * 折让金额
     */
    private BigDecimal amount;
    
    /**
     * 交易时间
     */
    private Date txnTime;

    /**
     * 检查码
     */
    private String checkCode;
    
    /**
     * 批次号
     */
    private String batchNo;
    
    private String traceNo;
    
    public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public CouponCheckRequest() {
    	amount = new BigDecimal("0");
		rebateRate = new BigDecimal("0");
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
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

	public String getPartnerNo() {
		return partnerNo;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public BigDecimal getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(BigDecimal rebateRate) {
		this.rebateRate = rebateRate;
	}

	public String getBizType() {
		return BizType;
	}

	public void setBizType(String bizType) {
		BizType = bizType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getCouponString() {
		return couponString;
	}

	public void setCouponString(String couponString) {
		if(couponString!=null){
			if(couponString.trim().length()==36){
				this.setSerialNo(couponString);
			}else {
				this.setCouponId(new Long(couponString));
			}
		}
		this.couponString = couponString;
	}
}
