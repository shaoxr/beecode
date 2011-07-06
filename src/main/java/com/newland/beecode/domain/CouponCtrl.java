package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class CouponCtrl {

    /**
     * 已冲正
     */
    public static final String VOID_FLAG_BACKOFF = "0";
    /**
     * 正常
     */
    public static final String VOID_FLAG_NORMAL = "1";
    @Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
	private Long id;

	/**
     * 礼券编号
     */
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
     * 折扣率
     */
    @Column
    private BigDecimal rebateRate;
    /**
     * 抵用金额
     */
    @Column
    private BigDecimal backAmount;
    /**
     * 折让金额
     */
    @Column
    private BigDecimal offAmount;
    /**
     * 原始金额
     * 
     */
    @Column
    private BigDecimal originalAmount;
    /**
     * 兑换物品
     */
    @Column
    private String exchangeName;
    /**
     * 兑换物品金额
     */
    @Column
    private BigDecimal exchangeAmount;

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
    
    public BigDecimal getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(BigDecimal backAmount) {
		this.backAmount = backAmount;
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
    public BigDecimal getOffAmount() {
		return offAmount;
	}

	public void setOffAmount(BigDecimal offAmount) {
		this.offAmount = offAmount;
	}

	public BigDecimal getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(BigDecimal originalAmount) {
		this.originalAmount = originalAmount;
	}
	 public Long getId() {
			return id;
	}

	public void setId(Long id) {
			this.id = id;
	}
	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public BigDecimal getExchangeAmount() {
		return exchangeAmount;
	}

	public void setExchangeAmount(BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}
    
    
}
