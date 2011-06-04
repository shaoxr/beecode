package sms;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.newland.beecode.service.SMSService;
import com.newland.beecode.service.impl.SMSServiceImpl;

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
	private static int produceTaskSleepTime = 2;  
	private static int consumeTaskSleepTime = 5000;  
	private static int produceTaskMaxNumber = 20;
	public static void main(String[] args)  {
		SMSService test=new SMSServiceImpl();
		//int re=test.registEx("2SDK-EMY-6688-JBVTN", "198538", "225480");
		//System.out.println(re);
		double d=0;
		try {
			d = test.getBalance("2SDK-EMY-6688-JBVTN", "225480");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(d);
		//int p=binding.sendSMS(softwareSerialNo, key, sendTime, mobiles, smsContent, addSerial, srcCharset,smsPriority, smsID);	    
		/*int p=0;
		try {
			//p = test.sendSMS("2SDK-EMY-6688-JBVTN", "198538", null, new String[]{"18650071122"}, "测试短信", "-----", "utf8",5, 888888);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   System.out.println(p);*/
	   // StatusReport[] s=test.getReport("2SDK-EMY-6688-JBVTN", "225480");
	   // for(int i=0;i<s.length;i++){
	    //	System.out.println(s[i]);
	  //  }
		
		
		
	}
	public static class ThreadPoolTask implements Runnable, Serializable {  
		private static final long serialVersionUID = 0;  
		// 保存任务所需要的数据  
		private Object threadPoolTaskData ;  
		  
		ThreadPoolTask(Object tasks) {  
		this . threadPoolTaskData = tasks;  
		}  
		  
		public void run() {  
		// 处理一个任务，这里的处理方式太简单了，仅仅是一个打印语句  
		System. out .println( "start .." + threadPoolTaskData );  
		try {  
		// // 便于观察，等待一段时间  
		Thread. sleep ( consumeTaskSleepTime );  
		} catch (Exception e) {  
		e.printStackTrace();  
		}  
		threadPoolTaskData = null ;  
		}  
		  
		public Object getTask() {  
		return this . threadPoolTaskData ;  
		}  
		}

}
