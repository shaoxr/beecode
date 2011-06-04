package com.newland.beecode.task;
/**
 * @author shaoxr:
 * @version 2011-5-30 下午08:59:08
 * 
 */
public class SmsSendParam {
	
	private String userName;
	
	private String key;
	
    private String password;
    
    private String mobile;
    
    private String content;
    
    private String sendId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

}
