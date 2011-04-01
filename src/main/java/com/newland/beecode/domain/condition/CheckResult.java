package com.newland.beecode.domain.condition;

public class CheckResult {
	
	private boolean pass;
	
	private String message;

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
