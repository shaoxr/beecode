package cn.emay.mms.test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import org.omg.IOP.Encoding;
import org.tempuri.MMS;
import org.tempuri.service.impl.MMSPostHelp;
import sun.misc.BASE64Encoder;
public class MMSTest {

	/**
	 * @param args
	 */
	 public static final String URL_SEND   = "http://mmsplat.eucp.b2m.cn/MMSCenterInterface/MMS.asmx/SendMMS";
	 public static final String URL_BALANCE= "http://mmsplat.eucp.b2m.cn/MMSCenterInterface/MMS.asmx/GetMMSCount";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MMS mms=new MMS();
		FileInputStream fs;
		try {
			fs = new FileInputStream("D:\\test.zip");
			byte[] Content=new byte[fs.available()];
			fs.read(Content,0,Content.length);
			fs.close();
			String mmsContent=new sun.misc.BASE64Encoder().encode(Content);
			System.out.println(mmsContent);
			byte[] a=new sun.misc.BASE64Decoder().decodeBuffer(mmsContent);
			File file=new File("D:\\tests.zip");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(a);
			fos.close();
			String param="userName=GD-ltwx&password=l123&title=1111"+
            "&userNumbers=18606063500&MMSContent="+mmsContent+"&sendType=1";
			//param="userName=GD-ltwx&password=l123";
			String resp=MMSPostHelp.HttpPost(URL_SEND,param);
			//String resp=mms.getMMSSoap().sendMMS("GD-ltwx", "l123", "test", "18606063500",Content , 1);
			System.out.println(resp);
			System.out.println(resp.substring(resp.length()-8, resp.length()-1));
			System.out.println("aaaaaaaaaaa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

