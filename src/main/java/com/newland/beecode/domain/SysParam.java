package com.newland.beecode.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author shaoxr:
 * @version 2011-7-16 上午11:14:39
 * 
 */
@Entity
public class SysParam {
	
	public static final String SEND_MGR_NAME="SEND_MGR";
	/**
	 * 电信号码自动转短信
	 */
	public static final String SEND_MGR_DX_SMS="1";
	/**
	 * 电信号码正常发彩信
	 */
	public static final String SEND_MGR_DX_MMS="2";
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
    private Long id;
	@Column
	private String paramName;
	@Column
	private String paramValue;
	@Column
	private String paramKey;
	
	

	@Version
    @Column(name = "version")
    private Integer version; 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

}
