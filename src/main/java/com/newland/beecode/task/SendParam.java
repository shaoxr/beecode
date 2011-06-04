package com.newland.beecode.task;


/**
 * @author shaoxr:
 * @version 2011-5-29 下午10:32:41
 * 
 */
public class SendParam {
	
	private String userName;
	
	private String password;
	
	private String title;
	
	private byte[] content;
	
	private String smsContent;
	
	private String mobile;
	
	private Long couponId;
	
	private Long sendListId;
	

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getSendListId() {
		return sendListId;
	}

	public void setSendListId(Long sendListId) {
		this.sendListId = sendListId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
