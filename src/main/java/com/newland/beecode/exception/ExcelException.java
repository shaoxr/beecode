package com.newland.beecode.exception;
/**
 * @author shaoxr:
 * @version 2011-5-14 上午09:04:55
 * 
 */
public class ExcelException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	private String code;

	private Object[] args;

	public ExcelException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public ExcelException(String code, String msg, Throwable cause) {
		super( msg, cause);
		this.code = code;
	}

	public ExcelException(String code, Object[] args, String msg) {
		super( msg);
		this.code = code;
		this.args = args;
	}

	public ExcelException(String code, Object[] args, String msg, Throwable cause) {
		super( msg, cause);
		this.code = code;
		this.args = args;
	}

	public ExcelException(Throwable cause) {
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
