package com.newland.beecode.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author shaoxr:
 * @version 2011-5-16 下午05:05:20
 * 
 */
@Entity
public class SendList {
	
	public static final String DICT_NAME="MMS_SEND_LIST_STATUS";
	
	public static final Integer STATUS_SENDING=0;
	
	public static final Integer STATUS_SENDED=1;
	
	public static final String DICT_NAME_MS_TYPE="MS_TYPE";
	
	public static final Integer MS_TYPE_SMS=0;
	
	public static final Integer MS_TYPE_MMS=1;
	
	public static final String MMS_RESP_FILE_NAME="mmsResp";
	
	public static final String SMS_RESP_FILE_NAME="smsResp";
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
	public Long id;
	@Column
	private Long totalCount;
	
	@Column
	private Long successCount;
	@Column
	private Integer sendStatus;
	@Column
	private String message;

	@Column
	private Date submitTime;
	@Column
	private Date endTime;
	@Column
	private String actName;
	@Column
	private Integer msType;
	@Column
	private Long actNo;

	public Long getActNo() {
		return actNo;
	}

	public void setActNo(Long actNo) {
		this.actNo = actNo;
	}

	public Integer getMsType() {
		return msType;
	}

	public void setMsType(Integer msType) {
		this.msType = msType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
