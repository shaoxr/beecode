package cn.emay.sms;

import com.ws.util.StatusReport;
import com.ws.util.service.SMSService;
import com.ws.util.service.impl.SMSServiceImpl;;

public class serviceTest {
	
	
	public static void main(String args[])throws Exception{
		SMSService test=new SMSServiceImpl();
		
		double d=test.getBalance("2SDK-EMY-6688-JBVTN", "225480");
	    System.out.println(d);
		//int p=binding.sendSMS(softwareSerialNo, key, sendTime, mobiles, smsContent, addSerial, srcCharset,smsPriority, smsID);	    
		int p=test.sendSMS("2SDK-EMY-6688-JBVTN", "123456", null, new String[]{"15806045105"}, "测试短信", "-----", "utf8",5, 888888);
	   System.out.println(p);
	   // StatusReport[] s=test.getReport("2SDK-EMY-6688-JBVTN", "225480");
	   // for(int i=0;i<s.length;i++){
	    //	System.out.println(s[i]);
	  //  }
		
	}


}
