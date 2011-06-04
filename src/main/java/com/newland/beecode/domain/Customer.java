package com.newland.beecode.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
public class Customer {
	
	public static final int LIMIT=2000;
	/**
	 * 未审核
	 */
	public static final Integer CUSTOMER_STATUS_UNCHECK=0;
	/**
	 * 已审核
	 */
	public static final Integer CUSTOMER_STATUS_CHECKED=1;
	/**
	 * 作废
	 */
	public static final Integer CUSTOMER_STATUS_INVILD=2;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
	public Long id;

	/**
	 * 姓名
	 */
	@Transient
	private String name;
	/**
	 * 电话
	 */
	@Transient
	private String mobile;
	/**
	 * 卡号
	 */
	@Transient
	private String account;
	/**
	 * 金额
	 */
	@Transient
	private BigDecimal amount;
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name="old_name")
	private String oldName;
	
	@Column(name="newName")
	private String newName;
	
	@Column(name="count")
	private long count;
	
	@Column(name="act_no")
	private Long actNo;
	
	@Column(name="file_status")
	private Integer fileStatus;
	
	@Column
	@Version
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Long getActNo() {
		return actNo;
	}

	public void setActNo(Long actNo) {
		this.actNo = actNo;
	}

}
