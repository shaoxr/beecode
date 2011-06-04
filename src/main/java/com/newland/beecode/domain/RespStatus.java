package com.newland.beecode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author shaoxr:
 * @version 2011-5-16 下午05:10:03
 * 
 */
@Entity
public class RespStatus {
	
	public static final String DICT_NAME="MMS_RESP_STATUS";
	
	public static final String RESP_SUCCESS="1";
	
	public static final String RESP_ERROR="2";
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
	private Long id;
	@Column
	private String couponId;
	@Column
	private String respStatus;
	@Column
	private String respDesc;
	@Column
	private Long mmsSendListId;

	public Long getMmsSendListId() {
		return mmsSendListId;
	}

	public void setMmsSendListId(Long mmsSendListId) {
		this.mmsSendListId = mmsSendListId;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}
	
	
	
	

}
