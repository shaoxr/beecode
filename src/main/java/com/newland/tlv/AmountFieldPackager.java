package com.newland.tlv;

import java.math.BigDecimal;

import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;

public class AmountFieldPackager extends DecimalFieldPackager{

	public AmountFieldPackager()
	{
		super(-1, null, 0);
	}
	
	public AmountFieldPackager(int len, String desc)
	{
		super(len, desc, 0);
	}
	
	public AmountFieldPackager(int len, String desc, int padding) 
	{
		super(len, desc);
		this.setPadding(padding);
	}
	
	@Override
	protected String getAsString(IField<?> field) {
		// TODO Auto-generated method stub
		BigDecimal newBd = ((BigDecimal)field.getValue()).multiply(new BigDecimal("100"));
		try {
			((Field<BigDecimal>)field).setValue(newBd);
		} catch (MessageException e) {
			e.printStackTrace();
		}
		
		String resultStr = super.getAsString(field);
		System.out.println(">>>>>>>"+resultStr);
		return resultStr;
	}

	@Override
	protected void setFieldValue(IField<?> field, String value) throws MessageException {
		super.setFieldValue(field, value);
		BigDecimal newBd = ((BigDecimal)field.getValue()).divide(new BigDecimal("100"));
		try {
			((Field<BigDecimal>)field).setValue(newBd);
		} catch (MessageException e) {
			e.printStackTrace();
		}		
	}

}
