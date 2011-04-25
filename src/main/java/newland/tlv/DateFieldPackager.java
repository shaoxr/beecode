package com.newland.tlv;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;

public class DateFieldPackager extends TLVFieldPackager {
	private static ThreadLocal<ThreadDateFormats> myDateFormaters = new ThreadLocal<ThreadDateFormats>();
	private String pattern;
	
	public DateFieldPackager()
	{
		this(-1, null, "yyyyMMdd");
	}
	
	public DateFieldPackager(int len, String desc)
	{
		this(len, desc, "yyyyMMdd");
	}
	
	public DateFieldPackager(int len, String desc, String pattern) 
	{
		super(len, desc);
		this.pattern = pattern;
	}
	
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
	
	@Override
	protected String getAsString(IField<?> field) {
		return getFormatter().format((Date)field.getValue());
	}

	@Override
	protected void setFieldValue(IField<?> field, String value) throws MessageException {
		try {
			((Field<Date>)field).setValue(getFormatter().parse(value));
		} catch (Exception e) {
			this.makeExceptionMessage(field, "unpacking, error format of value:"+value);
		}
	}
	
	public IField<?> createComponent(int fldNo) {
		return new Field<BigDecimal>(fldNo);
	}
	
	private DateFormat getFormatter() {
		ThreadDateFormats formats = myDateFormaters.get();
		if (formats == null) {
			formats = new ThreadDateFormats();
			myDateFormaters.set(formats);
		}
		return formats.getDateFormat(pattern);
    }
	
	private class ThreadDateFormats {
		Map<String, DateFormat> map = new Hashtable<String, DateFormat>();
		
		ThreadDateFormats() {}
		
		DateFormat getDateFormat(String pattern) {
			DateFormat formatter = map.get(pattern);
			if (formatter == null) {
				formatter =  new SimpleDateFormat(pattern);
	            map.put("pattern",formatter);
			}
			return formatter;
		}
	}
}
