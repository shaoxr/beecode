package com.newland.beecode.domain;

import javax.persistence.Entity;


public class Customer {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String mobile;

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

}
