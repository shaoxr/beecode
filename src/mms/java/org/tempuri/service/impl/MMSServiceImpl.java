package org.tempuri.service.impl;

import org.springframework.stereotype.Service;
import org.tempuri.service.MMSService;

import client.MMSLocator;
@Service("mmsService")
public class MMSServiceImpl implements MMSService{
	/**
	 * 网关发送
	 */
    public static final int SEND_TYPE_GATEWAY=1;
    /**
     * 卡发送
     */
    public static final int SEND_TYPE_CARD=2;
    
    
	public String[] sendMMS(String user, String password, String mobile,
			String title, byte[] mmsContent) throws Exception{
		MMSLocator client=new MMSLocator();
		String resp=client.getMMSSoap().sendMMS(user, password, title, mobile,mmsContent , SEND_TYPE_GATEWAY);
		
		String[] strs={resp,resp.substring(resp.length()-8, resp.length()-1)};
		return strs;
	}
	@Override
	public long getBalance(String user, String password, int sendType)throws Exception {
		MMSLocator client=new MMSLocator();
		long result=client.getMMSSoap().getMMSCount(user, password, sendType);
		return result;
	}
	

}
