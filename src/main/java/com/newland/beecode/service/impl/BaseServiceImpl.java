package com.newland.beecode.service.impl;

import com.newland.beecode.service.BaseService;

public class BaseServiceImpl implements BaseService{
	
	private String MMSUserName;
	
	private String MMSPassword;
	
	private String filePath;
	
	private String SMSUserName;
	
	private String SMSPassword;
	
	private String SMSKey;

	public String getMMSUserName() {
		return MMSUserName;
	}

	public String getMMSPassword() {
		return MMSPassword;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getSMSUserName() {
		return SMSUserName;
	}

	public String getSMSPassword() {
		return SMSPassword;
	}

	public String getSMSKey() {
		return SMSKey;
	}

	public void setMMSUserName(String mMSUserName) {
		MMSUserName = mMSUserName;
	}

	public void setMMSPassword(String mMSPassword) {
		MMSPassword = mMSPassword;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setSMSUserName(String sMSUserName) {
		SMSUserName = sMSUserName;
	}

	public void setSMSPassword(String sMSPassword) {
		SMSPassword = sMSPassword;
	}

	public void setSMSKey(String sMSKey) {
		SMSKey = sMSKey;
	}

}
