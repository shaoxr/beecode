package sms;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.axis.AxisFault;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.service.FileService;
import com.newland.beecode.service.SMSService;
import com.newland.beecode.service.impl.SMSServiceImpl;
import com.newland.beecode.task.SendParam;
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
	@Autowired
	private FileService fileService;
	@Test
	public void smsTest(){
		int re=0;
		try {
			SendParam sp=new SendParam();
			String s= this.fileService.getTextContent("507169060239",null);
			sp.setSmsContent(new String(s.getBytes("utf-8")));
			System.out.println(new String(s.getBytes()));
			sp.setTitle("20102232333333");
			sp.setMobile("18759178933");
			sp.setCouponId(new Long("507169060239"));
			//String resp=this.smsService.sendSMSByMontnets(sp);
			//System.out.println(resp);
			this.smsService.getBalanceByMontnets();
	} catch (Exception e) {
		if(e instanceof AxisFault){
			System.out.println("aaaaaaaaaaaaaaa");
		}
		System.out.println(e.getClass());
		e.printStackTrace();
	}
		
	}

}
