package com.newland.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewlandUtil {
	public static String float2StringByRebate(float l){
		BigDecimal b=new BigDecimal(l);
		String str=b.multiply(new BigDecimal(10)).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
		return str.length()>=2?str:"0"+str;
	}
	public static BigDecimal String2BigByRebate(String s){
		return new BigDecimal(s).divide(new BigDecimal(10));
	}
	public static String dataToString(Date date,String patten){
		if(date==null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		return sdf.format(date);
	}
	public static Date string2Date(String str,String patten){
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
