package barCode;

import jxl.format.RGB;

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
		for(int i=0;i<10;i++){
			System.out.println(Math.random());
		}
		this.barCodeService.genCode("http://218.66.48.231:3000/suboer/index.html", "119");
		//this.barCodeService.genCode("MEBKM:TITLE:Welcome to Newland!;URL:http\\://www.newland.com.cn;;", "002");
	}
	

}
