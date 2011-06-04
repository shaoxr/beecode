package barCode;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.service.BarCodeService;

/**
 * @author shaoxr:
 * @version 2011-6-1 下午08:58:24
 * 
 */
@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-*.xml"})
public class BarCodeTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private BarCodeService barCodeService;
	@Test
	public void genTest(){
		this.barCodeService.genCode("http://218.66.48.231:3000/easyPayment/goods_05.html", "05");
	}

}
