package sms;

import java.math.BigDecimal;

import com.newland.beecode.domain.MarketingAct;
import com.newland.utils.NewlandUtil;

/**
 * @author shaoxr:
 * @version 2011-5-16 下午04:15:59
 * 
 */
public class CommonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(NewlandUtil.formatBigDecimal(new BigDecimal(0.75),10));
		BigDecimal b=new BigDecimal(100.01);
		BigDecimal a=new BigDecimal(100.010);
		System.out.println(b.equals(a));
		
		System.out.println("aaaabbbb".indexOf("a"));
	}

}
