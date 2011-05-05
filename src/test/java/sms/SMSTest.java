package sms;

import com.ws.util.service.SMSService;
import com.ws.util.service.impl.SMSServiceImpl;

/**
 * @author shaoxr:
 * @version 2011-5-4 下午08:10:39
 * 
 */
public class SMSTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args)  {
		SMSService test=new SMSServiceImpl();
		//int re=test.registEx("2SDK-EMY-6688-JBVTN", "198538", "225480");
		//System.out.println(re);
		//double d=test.getBalance("2SDK-EMY-6688-JBVTN", "225480");
	   // System.out.println(d);
		//int p=binding.sendSMS(softwareSerialNo, key, sendTime, mobiles, smsContent, addSerial, srcCharset,smsPriority, smsID);	    
		int p=0;
		try {
			p = test.sendSMS("2SDK-EMY-6688-JBVTN", "198538", null, new String[]{"18650071122"}, "测试短信", "-----", "utf8",5, 888888);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   System.out.println(p);
	   // StatusReport[] s=test.getReport("2SDK-EMY-6688-JBVTN", "225480");
	   // for(int i=0;i<s.length;i++){
	    //	System.out.println(s[i]);
	  //  }
		
	}

}
