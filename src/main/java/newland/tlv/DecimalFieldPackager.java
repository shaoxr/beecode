package com.newland.tlv;

import java.math.BigDecimal;

import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;
import com.newland.message.convert.DecimalConverter;

public class DecimalFieldPackager extends TLVFieldPackager {
	private int padding = 2;
	
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	public int getPadding() {
		return this.padding;
	}
	public DecimalFieldPackager()
	{
		this(-1, null, 2);
	}
	
	public DecimalFieldPackager(int len, String desc)
	{
		this(len, desc, 2);
	}
	
	public DecimalFieldPackager(int len, String desc, int padding) 
	{
		super(len, desc);
		this.padding = padding;
	}
	
	@Override
	protected String getAsString(IField<?> field) {
		return DecimalConverter.convert((BigDecimal)field.getValue(), padding);
	}

	@Override
	protected void setFieldValue(IField<?> field, String value) throws MessageException {
		try {
			((Field<BigDecimal>)field).setValue(DecimalConverter.convert(value));
		} catch (Exception e) {
			this.makeExceptionMessage(field, "unpacking, error format of value:"+value);
		}
	}
	
	public IField<?> createComponent(int fldNo) {
		return new Field<BigDecimal>(fldNo);
	}
}
