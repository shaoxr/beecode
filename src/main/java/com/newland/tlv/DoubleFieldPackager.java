package com.newland.tlv;

import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;
import com.newland.message.convert.DoubleConverter;

public class DoubleFieldPackager extends TLVFieldPackager {
	private int padding = 2;
	
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	public int getPadding() {
		return this.padding;
	}
	public DoubleFieldPackager()
	{
		this(-1, null, 2);
	}
	
	public DoubleFieldPackager(int len, String desc)
	{
		this(len, desc, 2);
	}
	
	public DoubleFieldPackager(int len, String desc, int padding) 
	{
		super(len, desc);
		this.padding = padding;
	}
	
	@Override
	protected String getAsString(IField<?> field) {
		return DoubleConverter.convert((Double)field.getValue(), padding);
	}

	@Override
	protected void setFieldValue(IField<?> field, String value) throws MessageException {
		try {
			((Field<Double>)field).setValue(DoubleConverter.convert(value));
		} catch (Exception e) {
			this.makeExceptionMessage(field, "unpacking, error format of value:"+value);
		}
	}
	
	public IField<?> createComponent(int fldNo) {
		return new Field<Double>(fldNo);
	}
}
