package sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.service.impl.MarketingActServiceImpl;

/**
 * @author shaoxr:
 * @version 2011-5-15 下午08:18:05
 * 
 */
public class LoggerTest {

	/**
	 * @param args
	 */
	private static Log logger = LogFactory.getLog(LoggerTest.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			try {
				System.out.println("start....");
				throw new AppException("","");
			} catch (AppException e) {
				throw  e;
			}
		} catch (AppException e) {
			logger.error("", e);
		}

	}

}
