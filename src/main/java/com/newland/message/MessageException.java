package com.newland.message;

public class MessageException extends Exception {
	private static final long serialVersionUID = 6523742581273157521L;

	public MessageException() {
		super();
	}

	public MessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Throwable cause) {
		super(cause);
	}

}
