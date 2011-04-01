package com.newland.iso;

import com.newland.posp.Message;
import com.newland.posp.MessageKeyGenerator;

public class ISOTerminalKeyGenerator implements MessageKeyGenerator {
	private int terminalIdField = 41;
	private int traceNumberField = 11;

	public void setTerminalIdField(int terminalIdField) {
		this.terminalIdField = terminalIdField;
	}
	
	public void setTraceNumberField(int traceNumberField) {
		this.traceNumberField = traceNumberField;
	}
	
	public String getKey(Message m) {
		return (m.hasField(terminalIdField)?m.getString(terminalIdField):"") + "@" +
			(m.hasField(traceNumberField)?m.getString(traceNumberField):Long.toString(System.currentTimeMillis()));
	}
}
