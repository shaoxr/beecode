package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import client.MMSLocator;


public class FuckFuck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MMSLocator service=new MMSLocator();
		try {
			FileInputStream fs;
			fs = new FileInputStream("d:\\test.zip");
			byte[] Content=new byte[fs.available()];
			fs.read(Content,0,Content.length);
			fs.close();
			String str=service.getMMSSoap().sendMMS("GD-ltwx", "l123", "aaaa", "18650071122", Content, 1);
			System.out.println(str);
			long str2=service.getMMSSoap().getMMSCount("GD-ltwx", "l123", 1);
			System.out.println(str2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
