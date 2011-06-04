/*
 * File BizException.java
 * Created on 2004-4-13
 *
 */
package com.newland.beecode.exception;

/**
 * @description 应用运行异常类
 * @author seabao
 * @project IFX
 * @date 2004-4-13
 */
public class AppRTException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

	private Object[] args;

	public AppRTException(String code, String msg) {
		super("["+msg+"]");
		this.code = code;
	}

	public AppRTException(String code, String msg, Throwable cause) {
		super("["+msg+"]",cause);
		this.code = code;
	}

	public AppRTException(String code, Object[] args, String msg) {
		super( msg);
		this.code = code;
		this.args = args;
	}

	public AppRTException(String code, Object[] args, String msg, Throwable cause) {
		super( msg, cause);
		this.code = code;
		this.args = args;
	}

	public AppRTException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		code = string;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] objects) {
		args = objects;
	}

}