package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import com.newland.beecode.domain.report.MarketingActSummary;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MarketingAct {

	/**
	 * 字典名称
	 */
	public static final String DICT_KEY_NAME = "ACT_STATUS";
	/**
	 * 业务类型字典名称
	 */
	public static final String BUSINESS_TYPE = "BUSINESS_TYPE";
	/**
	 * 短信/彩信 类型字典
	 */
	public static final String MS_TYPE = "MS_TYPE";
	/**
	 * 卡检查字典
	 */
	public static final String DICT_KEY_NAME_CHECK_CARD = "BIND_CARD";

	/**
	 * 待审核
	 */
	public static final Integer STATUS_BEFORE_RECHECK = 0;

	/**
	 * 审核失败
	 */
	public static final Integer STATUS_RECHECK_FAIL = 1;

	/**
	 * 作废
	 */
	public static final Integer STAUS_DELETE = 2;

	/**
	 * 待发放
	 */
	public static final Integer STATUS_BEFORE_GIVE = 3;
	/**
	 * 发放中
	 */
	public static final Integer STATUS_SENDNG = 4;

	/**
	 * 已发放
	 */
	public static final Integer STATUS_AFTER_GIVE = 5;

	/**
	 * 已过期
	 */
	public static final Integer STATUS_EXPIRED = 6;

	/**
	 * 已关闭
	 */
	public static final Integer STATUS_CLOSED = 7;
	/**
	 * 绑定银行卡
	 */
	public static final String BIND_CARD_YES = "1";
	/**
	 * 不绑定银行卡
	 */
	public static final String BIND_CARD_NO = "0";

	public MarketingAct() {
		amount = new BigDecimal(0);
		rebateRate = new Float(0);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "100") })
	@Column
	private Long actNo;

	@Transient
	private String actStatusDesc;

	@Column
	private String actName;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date endDate;

	@Column
	private String actDetail;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Date genTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date sendTime;

	@Column
	private Integer times;

	@Column
	private String checkCode;

	@Column
	private Float rebateRate;

	/**
	 * 兑换券价值
	 */
	@Column
	private BigDecimal amount;

	@Column
	private Integer actStatus;
	/**
	 * 礼券数量
	 */
	@Column
	private Long couponSum;
	/**
	 * 彩信发放数量
	 */
	@Column
	private Long mmsSendSum;
	/**
	 * 短信发放数量
	 */
	@Column
	private Long smsSendSum;

	@Transient
	private Long sendErrSum;

	@Column
	private String bizNo;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Partner> partners = new HashSet<Partner>();

	@ManyToOne
	@JoinColumn(name = "marketing_catalog")
	private MarketingCatalog marketingCatalog;

	@Column
	private Long operNo;

	@Column
	private String mmsContent;

	@Column
	private String mmsTitle;

	@Column
	private String bindCard;

	@Column
	private String fileName;

	@Transient
	private String bizName;

	@Transient
	private MarketingActSummary summary;

	@Transient
	private MmsTemplate mmsTemplate;

	@Version
	@Column(name = "version")
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public MarketingActSummary getSummary() {
		return summary;
	}

	public void setSummary(MarketingActSummary summary) {
		this.summary = summary;
	}

	public String getActDetail() {
		return actDetail;
	}

	public void setActDetail(String actDetail) {
		this.actDetail = actDetail;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Long getActNo() {
		return actNo;
	}

	public void setActNo(Long actNo) {
		this.actNo = actNo;
	}

	public Integer getActStatus() {
		return actStatus;
	}

	public void setActStatus(Integer actStatus) {
		this.actStatus = actStatus;
	}

	public String getActStatusDesc() {
		return actStatusDesc;
	}

	public void setActStatusDesc(String actStatusDesc) {
		this.actStatusDesc = actStatusDesc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBindCard() {
		return bindCard;
	}

	public void setBindCard(String bindCard) {
		this.bindCard = bindCard;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public Long getCouponSum() {
		return couponSum;
	}

	public void setCouponSum(Long couponSum) {
		this.couponSum = couponSum;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public MarketingCatalog getMarketingCatalog() {
		return marketingCatalog;
	}

	public void setMarketingCatalog(MarketingCatalog marketingCatalog) {
		this.marketingCatalog = marketingCatalog;
	}

	public String getMmsContent() {
		return mmsContent;
	}

	public void setMmsContent(String mmsContent) {
		this.mmsContent = mmsContent;
	}

	public Long getMmsSendSum() {
		return mmsSendSum;
	}

	public void setMmsSendSum(Long mmsSendSum) {
		this.mmsSendSum = mmsSendSum;
	}

	public MmsTemplate getMmsTemplate() {
		return mmsTemplate;
	}

	public void setMmsTemplate(MmsTemplate mmsTemplate) {
		this.mmsTemplate = mmsTemplate;
	}

	public String getMmsTitle() {
		return mmsTitle;
	}

	public void setMmsTitle(String mmsTitle) {
		this.mmsTitle = mmsTitle;
	}

	public Long getOperNo() {
		return operNo;
	}

	public void setOperNo(Long operNo) {
		this.operNo = operNo;
	}

	public Set<Partner> getPartners() {
		return partners;
	}

	public void setPartners(Set<Partner> partners) {
		this.partners = partners;
	}

	public Float getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(Float rebateRate) {
		this.rebateRate = rebateRate;
	}

	public Long getSendErrSum() {
		return sendErrSum;
	}

	public void setSendErrSum(Long sendErrSum) {
		this.sendErrSum = sendErrSum;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getSmsSendSum() {
		return smsSendSum;
	}

	public void setSmsSendSum(Long smsSendSum) {
		this.smsSendSum = smsSendSum;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

}
