package com.newland.tlv;

import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;

public class StringFieldPackager extends TLVFieldPackager {
	public StringFieldPackager()
	{
		this(-1, null);
	}
	
	public StringFieldPackager(int len, String desc)
	{
		super(len, desc);
	}
	
	
	protected String getAsString(IField<?> field) {
		return (String)field.getValue();
	}

	protected void setFieldValue(IField<?> field, String value) throws MessageException {
		((Field<String>)field).setValue(value);
	}
}
