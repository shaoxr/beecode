package com.newland.tlv;

import java.util.Date;

import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;

public class IntFieldPackager extends TLVFieldPackager {
	public IntFieldPackager(){
		this(-1, null);
	}
	
	public IntFieldPackager(int len, String desc)
	{
		super(len, desc);
	}
	@Override
	protected String getAsString(IField<?> field) {
		return field.getValue().toString();
	}

	@Override
	protected void setFieldValue(IField<?> field, String value)
			throws MessageException {
		((Field<Integer>)field).setValue(Integer.valueOf(value));
	}
	
	@Override
	public IField<?> createComponent(int fldNo) {
		return new Field<Integer>(fldNo);
	}

}
