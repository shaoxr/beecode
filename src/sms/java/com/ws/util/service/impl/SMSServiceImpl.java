package com.ws.util.service.impl;

import java.util.List;

import com.ws.util.StatusReport;
import com.ws.util.service.SMSService;



public class SMSServiceImpl implements SMSService  {
	/**
     * 注册序列号
     * @param softwareSerialNo   软件序列号
     * @param key                要注册的关键字
     * @param serialpass         软件序列号密码
     */
	public int registEx(String softwareSerialNo, String key, String serialpass)throws Exception{
		com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new Exception("JAX-RPC ServiceException caught: " + jre);
        }
        //assertNotNull("binding is null", binding);

         int value = -3;
        //注册序列号
         value = binding.registEx("softwareSerialNo", "key", "serialpass");
		 return value;
	}
    	
	/**
     * 查询余额
     * @param softwareSerialNo   软件序列号
     * @param key                要注册的关键字，要和注册时的一样
     */
	public double getBalance(String softwareSerialNo, String key)throws Exception{
		com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new Exception("JAX-RPC ServiceException caught: " + jre);
        }
        //assertNotNull("binding is null", binding);

        //注册序列号
         double d=binding.getBalance(softwareSerialNo, key);
		 return d;
		
	}
        
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
			String srcCharset, int smsPriority,long smsID)throws Exception{
		com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new Exception("JAX-RPC ServiceException caught: " + jre);
        }
        //assertNotNull("binding is null", binding);

        //注册序列号
        int p=binding.sendSMS(softwareSerialNo, key, sendTime, mobiles, smsContent, addSerial, srcCharset,smsPriority, smsID);
	    return p;
	}

	@Override
	public StatusReport[] getReport(String softwareSerialNo, String key)
			throws Exception {
		com.ws.util.SDKServiceBindingStub binding = null;
		try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }catch(Exception e){
        	
        }
        StatusReport[] s=binding.getReport(softwareSerialNo, key);
       
		return s;
	}

}
