package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class CoupontCtrl {

    /**
     * 已冲正
     */
    public static final String VOID_FLAG_BACKOFF = "0";
    /**
     * 正常
     */
    public static final String VOID_FLAG_NORMAL = "1";
    /**
     * 礼券编号
     */
    @Id
    @Column
    private Long couponId;
    /**
     * 随机串号（二维码文本）
     */
    @Column
    private String serialNo;
    /**
     * 终端流水号
     */
    @Column
    private String traceNo;
    /**
     * 批次号
     */
    @Column
    private String batchNo;
    /**
     * 编码版本号（二维码文本编码格式版本）
     */
    @Column
    private String encodeVersion;
    /**
     * 商户终端编号
     */
    @Column
    private String deviceNo;
    /**
     * 商户编号
     */
    @Column
    private String partnerNo;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    private Date checkDay;
    /**
     * 检验日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date checkDate;
    /**
     * 折扣金额或兑换物品价值
     */
    @Column
    private BigDecimal amount;
    /**
     * 折扣率
     */
    @Column
    private BigDecimal rebateRate;
    /**
     * 业务代码
     */
    @Column
    private String businessType;
    /**
     * 冲正标志 
     * 0 - 冲正  
     * 1 - 未冲正
     */
    @Column
    private String voidFlag;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getCheckDay() {
        return checkDay;
    }

    public void setCheckDay(Date checkDay) {
        this.checkDay = checkDay;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getEncodeVersion() {
        return encodeVersion;
    }

    public void setEncodeVersion(String encodeVersion) {
        this.encodeVersion = encodeVersion;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getVoidFlag() {
        return voidFlag;
    }

    public void setVoidFlag(String voidFlag) {
        this.voidFlag = voidFlag;
    }
    
    
}
