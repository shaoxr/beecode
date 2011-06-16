package com.newland.beecode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.montnets.mms.CustomerMmsLocator;
import com.newland.beecode.service.BaseService;
import com.newland.beecode.service.MMSService;
import com.newland.beecode.task.SendParam;

import client.MMSLocator;
@Service("mmsService")
public class MMSServiceImpl implements MMSService{
	@Autowired
	private BaseService baseService;
	/**
	 * 网关发送
	 */
    public static final int SEND_TYPE_GATEWAY=1;
    /**
     * 卡发送
     */
    public static final int SEND_TYPE_CARD=2;
    
    
	public String[] sendMMS(SendParam mp) throws Exception{
		MMSLocator client=new MMSLocator();
		String resp=client.getMMSSoap().sendMMS(this.baseService.getMMSUserName(), this.baseService.getMMSPassword(), 
				mp.getTitle(), mp.getMobile(),mp.getContent() , SEND_TYPE_GATEWAY);
		
		String[] strs={resp,resp.substring(resp.length()-8, resp.length()-1)};
		return strs;
	}
	@Override
	public long getBalance(String user, String password, int sendType)throws Exception {
		MMSLocator client=new MMSLocator();
		long result=client.getMMSSoap().getMMSCount(user, password, sendType);
		return result;
	}
	@Override
	public String sendMMSByMontnets(SendParam mp) throws Exception {
		CustomerMmsLocator customer=new CustomerMmsLocator();
		return customer.getCustomerMmsSoap().sendMms(this.baseService.getMMSUserName(), this.baseService.getMMSPassword(), mp.getMobile(), mp.getBase64Content(), mp.getTitle());
	}
	

}
