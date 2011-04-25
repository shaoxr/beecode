package com.ws.util.service;

import java.util.List;

import com.ws.util.StatusReport;


public interface SMSService {
	      
	/**
     * 注册序列号
     * @param softwareSerialNo   软件序列号
     * @param key                要注册的关键字
     * @param serialpass         软件序列号密码
     */
	public int registEx(String softwareSerialNo, String key, String serialpass)throws Exception;
    	
	/**
     * 查询余额
     * @param softwareSerialNo   软件序列号
     * @param key                要注册的关键字，要和注册时的一样
     */
	public double getBalance(String softwareSerialNo, String key)throws Exception;
        
	/**
     * 短信发送
     * @param softwareSerialNo   软件序列号
     * @param key                要注册的关键字，要和注册时的一样
     * @param sendTime           定时短信的定时时间
     * @param mobiles            手机号码(字符串数组,最多为200个手机号码)
     * @param smsContent         短信内容
     * @param addSerial          扩展号码 
     * @param srcCharset         字符编码，默认为"GBK"
     * @param smsPriority        短信等级，范围1~5，数值越高优先级越高
     * @param smsPriority        短信ID
     *       
     */
	public int sendSMS(String softwareSerialNo, String key, String sendTime,
			String[] mobiles, String smsContent, String addSerial,
			String srcCharset, int smsPriority,long smsID)throws Exception;
	
	public StatusReport[] getReport(String softwareSerialNo, String key)throws Exception;
}
