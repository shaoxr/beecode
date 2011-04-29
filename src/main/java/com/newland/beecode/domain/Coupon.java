package com.newland.beecode.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Coupon {
	private static final Log logger = LogFactory.getLog(Coupon.class);
	/**
	 * 字典名称
	 */
	public static final String DICT_KEY_NAME = "COUPON_STATUS";
	/**
	 * 短信彩信状态字典名称 
	 */
	public static final String MMS_SMS_STATUS_KEY_NAME="COUPON_MMS_STATUS";

	/**
	 * 有效
	 */
	public static final Integer STATUS_VALID = 1;

	/**
	 * 无效
	 */
	public static final Integer STATUS_INVALID = 0;

	/**
	 * 已用完优惠
	 */
	public static final Integer STATUS_FINISH = 4;

	/**
	 * 过期
	 */
	public static final Integer STATUS_EXPIRED = 2;

	/**
	 * 挂失
	 */
	public static final Integer STATUS_LOST = 3;
	/**
	 * 彩信等待发送
	 */
	public static final Integer MMS_STATUS_WAIT=0;
	/**
	 * 彩信已经发送
	 */
	public static final Integer MMS_STATUS_SNED=1;
	/**
	 * 彩信成功接收
	 */
	public static final Integer MMS_STATUS_REC_SUCCESS=2;
	/**
	 * 彩信接收失败
	 */
	public static final Integer MMS_STATUS_REC_ERROR=3;
	/**
	 * 彩信发送失败
	 */
	public static final Integer MMS_STATUS_SEND_ERROR=4;
	
	/**
	 * 短信等待发送
	 */
	public static final Integer SMS_STATUS_WAIT=0;
	/**
	 * 短信已经发送
	 */
	public static final Integer SMS_STATUS_SNED=1;
	/**
	 * 短信发送失败
	 */
	public static final Integer SMS_STATUS_SEND_ERROR=4;
	/**
	 * 短信成功接收
	 */
	public static final Integer SMS_STATUS_REC_SUCCESS=2;
	/**
	 * 短信接收失败
	 */
	public static final Integer SMS_STATUS_REC_ERROR=3;
	
	
	
	/**
	 * 兑换券
	 */
	public static final String BIZ_TYPE_EXCHANGE="00";
	/**
	 *  优惠券
	 */
	public static final String BIZ_TYPE_DISCOUNT="01";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "com.newland.utils.RandomLoMultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "3000") })
	private Long couponId;

	@Column
	private String acctNo;

	@Column
	private String acctMobile;

	@Column
	private String checkCode;

	@Column
	private String serialNo;

	@Column
	private Integer couponStatus;
	
	@Column
	private Integer mmsStatus;
	
	@Column
	private String mmsDesc;
	
	@Column
	private String smsDesc;
	
	@Column
	private Integer smsStatus;

	@Transient
	private String couponStatusDesc;

	@Column
	private Float rebateRate;

	@Column
	private Integer times;
	
	@Column
	private String acctName;
	
	@Column
	private String mmsId;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date genTime;

	@Column
	private String businessType;

	@Column
	private Integer remainTimes;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="marketing_act")
	private MarketingAct marketingAct;
	
	@Transient
	private String mmsStatusDesc;
	
	@Transient
	private String smsStatusDesc;
	
	@Column
	@Version
	private Integer version;
	
	//start getter/setter
	

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctMobile() {
		return acctMobile;
	}

	public void setAcctMobile(String acctMobile) {
		this.acctMobile = acctMobile;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}

	public Integer getMmsStatus() {
		return mmsStatus;
	}

	public void setMmsStatus(Integer mmsStatus) {
		this.mmsStatus = mmsStatus;
	}

	public String getMmsDesc() {
		return mmsDesc;
	}

	public void setMmsDesc(String mmsDesc) {
		this.mmsDesc = mmsDesc;
	}

	public String getSmsDesc() {
		return smsDesc;
	}

	public void setSmsDesc(String smsDesc) {
		this.smsDesc = smsDesc;
	}

	public Integer getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(Integer smsStatus) {
		this.smsStatus = smsStatus;
	}

	public Float getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(Float rebateRate) {
		this.rebateRate = rebateRate;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getMmsId() {
		return mmsId;
	}

	public void setMmsId(String mmsId) {
		this.mmsId = mmsId;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getRemainTimes() {
		return remainTimes;
	}

	public void setRemainTimes(Integer remainTimes) {
		this.remainTimes = remainTimes;
	}

	public MarketingAct getMarketingAct() {
		return marketingAct;
	}

	public void setMarketingAct(MarketingAct marketingAct) {
		this.marketingAct = marketingAct;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setCouponStatusDesc(String couponStatusDesc) {
		this.couponStatusDesc = couponStatusDesc;
	}

	public void setMmsStatusDesc(String mmsStatusDesc) {
		this.mmsStatusDesc = mmsStatusDesc;
	}

	public void setSmsStatusDesc(String smsStatusDesc) {
		this.smsStatusDesc = smsStatusDesc;
	}
	
	
	//end getter/setter
	//TODO 改成公共的数据字典服务提供
//	public String getMmsStatusDesc(){
//		return DictView.getDescByKeyName(MMS_SMS_STATUS_KEY_NAME, this.getMmsStatus().toString());
//		
//	}
//	
//
//	public String getSmsStatusDesc(){
//		return DictView.getDescByKeyName(MMS_SMS_STATUS_KEY_NAME, this.getSmsStatus().toString());
//	}
//
//	public String getCouponStatusDesc() {
//		return DictView.getValuesByKeyName(DICT_KEY_NAME).get(
//				couponStatus.toString());
//	}

	



}
