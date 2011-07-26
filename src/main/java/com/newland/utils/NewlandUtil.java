package com.newland.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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
	public static String formatString(String str,int len){
			if(str.length()>len){
				return str.substring(0,len-1);
			}
			int temp=len-str.length();
			for(int i=0;i<temp;i++){
				str="0"+str;
			}
			return str;
	}
	public static String formatBigDecimal(BigDecimal b,int len){
		return formatString(b.movePointRight(2).setScale(0,BigDecimal.ROUND_HALF_UP).toString(),len);
	}
	public static String formatBigDecimal(BigDecimal b){
		return b.movePointRight(2).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
	}
	public static BigDecimal String2BigDecimal(String s){
		return new BigDecimal(s).movePointLeft(2);
	}
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]+"); 
	    return pattern.matcher(str).matches();    
	 }

}
