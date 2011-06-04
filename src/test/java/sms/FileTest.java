package sms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author shaoxr:
 * @version 2011-5-15 上午10:56:56
 * 
 */
public class FileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ZipInputStream   in   =   new   ZipInputStream(new   FileInputStream( 
					"D:/beecode/mms_get_temp/mms.zip")); 
                    String rootPath="aaaa";
                    File dir=new File("D:/beecode/mms_get_temp/"+rootPath);
                    if(dir.exists()){
                    	System.out.println(dir.getAbsolutePath()+"---------"+dir.getName());
                    	dir.delete();
                    }
                    dir.mkdir();
					ZipEntry   entry   =   null; 
					while   ((entry   =   in.getNextEntry())   !=   null)   { 
					String   entryName   =   entry.getName(); 
					if   (entry.isDirectory())   { 
						System.out.println(entryName);
					File   file   =   new   File("D:/beecode/mms_get_temp/"+rootPath + "/"+  entryName); 
					file.mkdirs(); 
					System.out.println( "创建文件夹: "   +   entryName); 
					}   else   { 
						System.out.println(entryName);
					OutputStream   os   =   new   FileOutputStream("D:/beecode/mms_get_temp/"+rootPath  +"/" +   entryName); 

					//   Transfer   bytes   from   the   ZIP   file   to   the   output   file 
					byte[]   buf   =   new   byte[1024]; 
					int   len; 
					while   ((len   =   in.read(buf))   >   0)   { 
					os.write(buf,   0,   len); 
					} 
					os.close(); 
					in.closeEntry(); 
					System.out.println( "解压文件: "   +   entryName); 
					} 
					} 
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
