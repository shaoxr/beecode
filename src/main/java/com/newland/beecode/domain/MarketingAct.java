package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Subselect;
import org.springframework.format.annotation.DateTimeFormat;

import com.newland.beecode.domain.report.MarketingActSummary;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MarketingAct {
	
	public static final String CHECK_CODE_REGEX=";";
	
	public static final String NEW_LINE="\r\n";
    public static final String FILE_NAME_REGEX="###";
    
    public static final String FILE_NAME_REGEX_IN="@@";
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
	 * 待添加客户
	 */
	public static final Integer STATUS_WAIT_ADD_CUSTOMER=0;

	/**
	 * 待审核
	 */
	public static final Integer STATUS_BEFORE_RECHECK = 1;
	/**
	 * 追加客户
	 */
	public static final Integer STATUS_APPEND_CUSTOMER=2;

	/**
	 * 审核失败
	 */
	public static final Integer STATUS_RECHECK_FAIL = 3;

	/**
	 * 作废
	 */
	public static final Integer STAUS_DELETE = 4;

	/**
	 * 待发放
	 */
	public static final Integer STATUS_BEFORE_GIVE = 5;
	/**
	 * 发放中
	 */
	public static final Integer STATUS_SENDNG = 6;

	/**
	 * 已发放
	 */
	public static final Integer STATUS_AFTER_GIVE = 7;

	/**
	 * 已过期
	 */
	public static final Integer STATUS_EXPIRED = 8;

	/**
	 * 已关闭
	 */
	public static final Integer STATUS_CLOSED = 9;
	/**
	 * 短信是否正在发放--NO
	 */
	public static final Integer SMS_SENDING_NO=0;
	/**
	 * 短信是否正在发放--YES
	 */
	public static final Integer SMS_SENDING_YES=1;
	/**
	 * 彩信是否正在发放--YES 
	 */
	public static final Integer MMS_SENDING_YES=1;
	/**
	 * 彩信是否正在发放--NO
	 */
	public static final Integer MMS_SENDING_NO=0;
	
	/**
	 * 绑定银行卡
	 */
	public static final String BIND_CARD_YES = "1";
	/**
	 * 不绑定银行卡
	 */
	public static final String BIND_CARD_NO = "0";
	
	public static final Integer IMPORT_TYPE_GLOBAL=2;
	
	public static final Integer IMPORT_TYPE_EXCEL=1;

	public MarketingAct() {
		amount = new BigDecimal(0);
		rebateRate = new BigDecimal(0);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
    @GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
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
	private BigDecimal rebateRate =new BigDecimal(0);

	/**
	 * 兑换券价值
	 */
	@Column
	private BigDecimal amount =new BigDecimal(0);

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

	@ManyToMany
	@JoinTable(name="marketing_act_terminal",joinColumns=@JoinColumn(name="marketing_act"),inverseJoinColumns=@JoinColumn(name="terminal"))
    private Set<Terminal> terminals = new HashSet<Terminal>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "marketing_catalog")
	private MarketingCatalog marketingCatalog;

	@Column
	private Long operNo;

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

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "mms_template_id")
	private MmsTemplate mmsTemplate;

	@Version
	@Column(name = "version")
	private Integer version;
	@Column
	private Integer mmsSending;
	@Column
	private Integer smsSending;
	
	@Column
	private BigDecimal maxAmount=new BigDecimal(0);
	
	@Column
	private BigDecimal backRate=new BigDecimal(0);
	@Column
	private Integer importType;

	/**
	 * 兑换物名称
	 */
	@Column
	private String exchangeName;

	@Transient
	private MsStatus msStatus;
	@Transient
	private String exContent="...";
	/**
	 * 兑换物品后缀
	 */
	@Column
	private String suffix;
	
	@Transient
	private List<Customer> unCheckCustomers;
	@Transient
	private List<Customer> checkedCustomers;
	
	public List<Customer> getUnCheckCustomers() {
		return unCheckCustomers;
	}

	public void setUnCheckCustomers(List<Customer> unCheckCustomers) {
		this.unCheckCustomers = unCheckCustomers;
	}
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public List<Customer> getCheckedCustomers() {
		return checkedCustomers;
	}

	public void setCheckedCustomers(List<Customer> checkedCustomers) {
		this.checkedCustomers = checkedCustomers;
	}

	public Integer getMmsSending() {
		return mmsSending;
	}

	public void setMmsSending(Integer mmsSending) {
		this.mmsSending = mmsSending;
	}

	public Integer getSmsSending() {
		return smsSending;
	}

	public void setSmsSending(Integer smsSending) {
		this.smsSending = smsSending;
	}
	public MsStatus getMsStatus() {
		return msStatus;
	}

	public void setMsStatus(MsStatus msStatus) {
		this.msStatus = msStatus;
	}

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


	public Set<Terminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(Set<Terminal> terminals) {
		this.terminals = terminals;
	}

	public BigDecimal getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(BigDecimal rebateRate) {
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
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getBackRate() {
		return backRate;
	}

	public void setBackRate(BigDecimal backRate) {
		this.backRate = backRate;
	}
	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	public Integer getImportType() {
		return importType;
	}

	public void setImportType(Integer importType) {
		this.importType = importType;
	}
	public String getExContent() {
		return exContent;
	}

	public void setExContent(String exContent) {
		this.exContent = exContent;
	}
}
