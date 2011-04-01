package org.tempuri.service.impl;

import org.springframework.stereotype.Service;
import org.tempuri.MMS;
import org.tempuri.service.MMSService;
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
    
    public static final String URL_BALANCE= "http://mmsplat.eucp.b2m.cn/MMSCenterInterface/MMS.asmx/GetMMSCount";
    
    public static final String URL_SEND   = "http://mmsplat.eucp.b2m.cn/MMSCenterInterface/MMS.asmx/SendMMS";
	public String[] sendMMS(String user, String password, String mobile,
			String title, byte[] mmsContent) throws Exception{
		MMS mms=new MMS();
		String resp=mms.getMMSSoap().sendMMS(user, password, title, mobile,mmsContent , SEND_TYPE_GATEWAY);
		
		String[] strs={resp,resp.substring(resp.length()-8, resp.length()-1)};
		return strs;
	}
	public String[] _sendMMS(String user, String password, String mobile,
			String title, byte[] mmsContent)throws Exception{
		String param="userName="+user+"&password="+password+"&title="+title+
		             "&userNumbers="+mobile+"&MMSContent="+String.valueOf(mmsContent)+"&MMSContent="+String.valueOf(mmsContent)+"&sendType=1";
		String resp=MMSPostHelp.HttpPost(URL_SEND,param);
		
		String[] strs={resp,resp.substring(resp.length()-8, resp.length()-1)};
		return strs;
	}
	@Override
	public int getBalance(String user, String password, int sendType)throws Exception {
		String param="userName="+user+"&password="+password+"&sendType="+sendType;
		String result=MMSPostHelp.HttpPost(URL_BALANCE,param);
		int count=0;
	    count =MMSResultParserHelp.BalanceParser(result);
		return count;
	}
	

}
