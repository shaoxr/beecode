package com.newland.utils;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.MultipleHiLoPerTableGenerator;

public class RandomLoMultipleHiLoPerTableGenerator extends MultipleHiLoPerTableGenerator {
	
	private static final DecimalFormat df = new DecimalFormat("000000");
	//TODO 每天使用新的时间种子生成器
	
	@Override
	public synchronized Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		Long id = (Long)super.generate(session, object);
		Long randomCode = (long)(Math.random()* 100000);
		
		return Long.valueOf(id+df.format(randomCode));
	}
	
}
