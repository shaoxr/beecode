package mms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.service.MMSService;
import com.newland.beecode.service.SMSService;

/**
 * @author shaoxr:
 * @version 2011-5-21 上午09:51:25
 * 
 */
@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-*.xml"})
public class MmsTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private MMSService mmsService;
	@Autowired
	private SMSService smsService;
	@Test
	public void sendTest(){
		int rep=1;
		try {
			//rep=this.smsService.registEx("2SDK-EMY-6688-JBYQQ", "198538", "175626");
			//double b=this.smsService.getBalance("2SDK-EMY-6688-JBYQQ", "198538");
			//System.out.println(b);
			long mb=this.mmsService.getBalance("GD-zynn", "zn1922", 1);
			System.out.println(mb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
