package com.newland.beecode.domain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import com.newland.beecode.service.MarketingActService;

@RooIntegrationTest(entity = MarketingAct.class)
public class MarketingActIntegrationTest {

	@Autowired
	private MarketingActService marketingActService;

	static final int BUFFER = 2048;

	@Test
	public void testMarkerMethod() throws InterruptedException {
		// marketingActService.checkMarketingAct(Long.getLong("707"),
		// MarketingAct.STATUS_BEFORE_GIVE);
		// Thread.sleep(30 * 1000);
		// MarketingAct.findByCatalog(new Long(1));

		// doZip("D:\\fuckyou\\fuck");
		doZip();
	}

	public void doZip() {
		try {
			BufferedInputStream bufStr = null;
			FileOutputStream dest = new FileOutputStream(
					"D:\\fuckyou\\zipDir\\myfiles.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			byte data[] = new byte[BUFFER];
			File files[] = {new File("D:\\fuckyou\\text\\content.txt"),new File("D:\\tmp\\750320470761300934272218.gif")};
			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(files[i]);
				bufStr = new BufferedInputStream(fi, BUFFER);
				String fileName="";
				if(files[i].getName().indexOf(".gif")>=0){
					fileName="1.gif";
				}else if(files[i].getName().indexOf(".txt")>=0){
					fileName="1.txt";
				}else{
					fileName=files[i].getName();
				}
				ZipEntry entry = new ZipEntry(fileName);
				out.putNextEntry(entry);
				int count;
				while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				bufStr.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
