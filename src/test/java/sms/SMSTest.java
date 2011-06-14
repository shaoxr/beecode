package sms;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.service.SMSService;
import com.newland.beecode.service.impl.SMSServiceImpl;
import com.ws.util.StatusReport;

/**
 * @author shaoxr:
 * @version 2011-5-4 下午08:10:39
 * 
 */
@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-*.xml"})
public class SMSTest extends AbstractJUnit4SpringContextTests{

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static int produceTaskSleepTime = 2;  
	private static int consumeTaskSleepTime = 5000;  
	private static int produceTaskMaxNumber = 20;
	@Autowired
	private SMSService smsService;
	@Test
	public void smsTest(){
		int re=0;
		try {
			//re = smsService.registEx("2SDK-EMY-6688-JBVTN", "198538", "225480");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(re);
		double d=0;
		try {
			d = smsService.getBalance("2SDK-EMY-6688-JBVTN", "198538");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(d);
	    int p=0;
		try {
			//p = smsService.sendSMS( null, new String[]{"18650071122"}, "测试短信测试短信短信测试短信测试", "-----", "utf8",2, 111111);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   System.out.println(p);
	   
	   try {
		StatusReport[] s=this.smsService.getReport("2SDK-EMY-6688-JBVTN", "198538");
		for(StatusReport st:s){
			System.out.println(st.getMemo());
			System.out.println(st.getMobile());
			System.out.println(st.getReportStatus());
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

}
