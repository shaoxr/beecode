/**
 * 
 */
package com.newland.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author linwy
 * 
 */
public class Util {

	public Util() {
	}

	private static Log log = LogFactory.getLog(Util.class);

	/*
	 * 功能: 验证字段值是否为空,用于数据更新前 参数: list 数据表 CheckField 验证值不为空的字段组 返回值: 1:不为空 0:为空
	 */
	public static int IsNullFieldValue(Hashtable list, Vector CheckField) {
		int st = 1;
		String temp;
		for (int i = 0; i < CheckField.size(); i++) {
			temp = Util.getNulltoStr(list.get(CheckField.get(i)));
			if (Util.bIsEmpty(temp)) {
				st = 0;
				break;
			}
		}
		return st;
	}

	// null转化为string
	public static String getNulltoStr(Object o) {
		String str = "";
		if (o != null)
			str = String.valueOf(o);
		return str;
	}
	//null转化为int
	public static int getNulltoInt(Object o) {
		int str = 0;
		if (o != null)
			str = Integer.parseInt((String) o);
		return str;
	}

	public static String[] getNulltoStr(String temp[]) {
		String str[] = null;

		for (int i = 0; i < temp.length; i++) {
			str[i] = getNulltoStr(temp[i]);
		}
		return str;
	}

	// 判断是否不为空
	public static boolean bIsEmpty(Object obj) {
		boolean bisNull = false;
		if (obj == null) {
			bisNull = true;
		} else if (obj instanceof String) {
			String str = obj.toString();
			if (str == "" || str.trim().equals(""))
				bisNull = true;
		}
		return bisNull;
	}

	// 返回一个子符串
	public static String Copy(String source, int beginIndex, int Count) {
		if (source.length() < Count)
			Count = source.length();
		if (beginIndex < 0)
			beginIndex = 0;
		return source.substring(beginIndex, Count);
	}

	// 返回一个子符串
	public static String Copy(Object source, int beginIndex, int Count) {
		String str = getNulltoStr(source);
		return Copy(str, beginIndex, Count);
	}

	// 字符串转换成整型
	public static int StrToInt(String str) {
		if (str == null || str == "" || str.trim().equals(""))
			return 0;// str = "0";
		return Integer.parseInt(str);
	}

	// Object 转换成整型
	public static int StrToInt(Object o) {
		String str = getNulltoStr(o);
		return StrToInt(str);
	}

	// 表格输出时列为空,不足列
	public static String getTableTd(Object source) {
		String str = getNulltoStr(source);
		if (Util.bIsEmpty(str))
			str = "&nbsp;";
		return str;
	}

	// 表格输出时列为空,不足列
	public static String getTableTd(String source) {
		String str = getNulltoStr(source);
		if (Util.bIsEmpty(str))
			str = "&nbsp;";
		return str;
	}

	public static byte[] getStrToUTF8(Object source) {
		try {
			byte[] temp = Util.getNulltoStr(source).getBytes("UTF-8");
			return temp;
		} catch (UnsupportedEncodingException e) {
			log.error("编码转换异常！[" + source + "]", e);
			return null;
		}
	}

	public static String getByteToStr(byte[] b) {
		try {
			return new String(b, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("编码转换异常！  ", e);
			return null;
		}
	}


	public static boolean isAfterNow(String str, String format) {
		boolean flag = false;
		Date date = null;
		if (Util.bIsEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(str);
			flag = date.after(new Date());
		} catch (Exception e) {
			log.error("时间格式转换异常！ ", e);
			return false;
		}
		return flag;
	}
	public static boolean isAfterNow(String str) {
		return isAfterNow(str, null);
	}
	/**
	 * 获取当前日期yyyyMMdd
	 * @return
	 */
	public static String getDayForStr(){
		return Util.getDayForStr(null, null);
	}
	
	public static String getDayForStr(Date date){
		return Util.getDayForStr(date, null);
	}
	
	public static String getDayForStr(Date date,String pattern){
		date = date==null?new Date():date;
		pattern = Util.bIsEmpty(pattern)?"yyyyMMdd":pattern;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return Util.getNulltoStr(sdf.format(date));
	}
	/**
	 * 获取当前日期HHmmss
	 * @return
	 */
	public static String getTimeForStr(){
		return Util.getTimeForStr(null, null);
	}
	
	public static String getTimeForStr(Date date){
		return Util.getTimeForStr(date,null);
	}
	
	public static String getTimeForStr(Date date,String pattern){
		date = date==null?new Date():date;
		pattern = Util.bIsEmpty(pattern)?"HHmmss":pattern;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return Util.getNulltoStr(sdf.format(new Date()));
	}
	
	public static Date formatStrToDate(String date,String pattern){
		pattern = Util.bIsEmpty(pattern)?"yyyyMMdd":pattern;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		date = Util.bIsEmpty(date)? sdf.format(new Date()):date;
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			log.warn("date format error!");
			return null;
		}
	}
	public static Date formatStrToDate(String date){
		return formatStrToDate(date,null);
	}
	
	public static boolean compareTo(String start,String end,String pattern,int day){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar startCal=Calendar.getInstance();
		Calendar endCal=Calendar.getInstance();
		try {
			startCal.setTime(sdf.parse(start));
			endCal.setTime(sdf.parse(end));
		} catch (ParseException e) {
			log.warn("date format error!");
		}
		startCal.add(Calendar.DAY_OF_MONTH, day);
	     return  endCal.before(startCal);
		
	}
}
