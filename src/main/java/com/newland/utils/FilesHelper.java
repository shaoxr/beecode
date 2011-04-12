package com.newland.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FilesHelper {

	public static String path = "c://temp//";
	
	public static String TEXT_PATH="D:\\beecode\\text\\";
	
	public static String IMAGE_PATH="D:\\beecode\\images\\";
	
	public static String ZIP_PATH="D:\\beecode\\zip\\";
	public static String SMIL_PATH="D:\\beecode\\smil\\";
	
	public static final int BUFFER = 2048;

	public static void storeFile(MultipartFile file, String fileName) throws IOException {
		File nf = new File(path + fileName);
		if (nf.exists()) {
			nf.delete();
		}
		nf.createNewFile();
		FileOutputStream fos = new FileOutputStream(nf);
		fos.write(file.getBytes());
		fos.close();
	}

	public static FileReader getActFile(String fileName) throws FileNotFoundException{
		File file = new File(path + fileName);
		return new FileReader(file);
		
	}
	public static String getTextContent(String fileName)throws IOException{
		  File file = new File(TEXT_PATH+fileName+".txt");
	        BufferedReader reader = null;
	        StringBuffer sb=new StringBuffer();
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	            	sb.append(tempString);
	            }
	            reader.close();
	        } catch (IOException e) {
	            throw new IOException("",e);
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        return sb.toString();
	}
	public static String getPath(String fileName){
		return path+fileName;
	}
	public static void genTextFile(String content,Long couponId){
		FileWriter fw ;
		try {
			fw=new FileWriter(TEXT_PATH+couponId+".txt");
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			
		}finally{
			
		}
		
	}
	public static byte[] getZIPByte(Long couponId)throws Exception{
		File files[] = {new File(TEXT_PATH+couponId+".txt"),new File(IMAGE_PATH+couponId+".gif"),new File(SMIL_PATH+"beecode.smil")};
		String zipPath=ZIP_PATH+"beecode.zip";
		doZip(files,zipPath);
		
		FileInputStream fs;
		byte[] Content=null;
			fs = new FileInputStream(zipPath);
			Content=new byte[fs.available()];
			fs.read(Content,0,Content.length);
			fs.close();
		
		return Content;
	}
	public static void doZip(File[] files,String zipPath) throws Exception{
			BufferedInputStream bufStr = null;
			FileOutputStream dest = new FileOutputStream(zipPath);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			byte data[] = new byte[BUFFER];
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
	}
}
