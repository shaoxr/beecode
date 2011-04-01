package com.newland.beecode.service.impl;

import com.newland.beecode.service.BaseService;

public class BaseServiceImpl implements BaseService{
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String userName;
	
	private String passWord;
	
	private String path;

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getPassWord() {
		return this.passWord;
	}

	@Override
	public String getPath() {
		return this.path;
	}

}
