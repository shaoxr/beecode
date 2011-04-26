package com.newland.posp;

import com.newland.message.FieldMap;

public class Message extends FieldMap {
	private static final long serialVersionUID = 1L;
	protected Header header;
	
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public Header getHeader() {
		return this.header;
	}
}
