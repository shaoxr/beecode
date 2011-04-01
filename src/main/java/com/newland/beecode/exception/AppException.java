package com.newland.beecode.exception;

public class AppException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 异常码
	 */
	private String code;
	
	/**
	 * 异常消息，用于日志记录
	 */
	private String msg;
	
	/**
	 * 异常上下文
	 */
	private Throwable exception;

	public AppException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public AppException(String code, String msg, Throwable t){
		this.code = code;
		this.msg = msg;
		this.exception = t;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
	
	
}
