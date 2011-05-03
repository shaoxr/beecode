package com.newland.beecode.service.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.service.BaseService;
import com.newland.beecode.service.FileService;

/**
 * @author shaoxr:
 * @version 2011-4-29 下午04:23:09
 * 
 */
@Service(value="fileService")
public class FileServiceImpl implements FileService{
	@Autowired
	private BaseService baseService;
	
	public static final String TEMP="temp/";
	
    public static final String TEXT_PATH="text/";
	
	public static final String IMAGE_PATH="images/";
	
	public static final String ZIP_PATH="zip/";
	
	public static final String SMIL_PATH="smil/";
	
	public static final int BUFFER = 2048;
	public void storeFile(MultipartFile file, String fileName) throws IOException {
		File nf = new File(baseService.getFilePath()+TEMP + fileName);
		if (nf.exists()) {
			nf.delete();
		}
		nf.createNewFile();
		FileOutputStream fos = new FileOutputStream(nf);
		fos.write(file.getBytes());
		fos.close();
	}

	public FileReader getActFile(String fileName) throws FileNotFoundException{
		File file = new File(this.baseService.getFilePath()+TEMP + fileName);
		return new FileReader(file);
		
	}
	public String getTextContent(String fileName)throws IOException{
		  File file = new File(this.baseService.getFilePath()+TEXT_PATH+fileName+".txt");
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
	public String getPath(String fileName){
		return this.baseService.getFilePath()+TEMP+fileName;
	}
	public void genTextFile(String content,Long couponId){
		FileWriter fw ;
		try {
			fw=new FileWriter(this.baseService.getFilePath()+TEXT_PATH+couponId+".txt");
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			
		}finally{
			
		}
		
	}
	public  byte[] getZIPByte(Long couponId)throws Exception{
		File files[] = {new File(this.baseService.getFilePath()+TEXT_PATH+couponId+".txt"),new File(this.baseService.getFilePath()+IMAGE_PATH+couponId+".gif"),new File(this.baseService.getFilePath()+SMIL_PATH+"beecode.smil")};
		String zipPath=this.baseService.getFilePath()+ZIP_PATH+"beecode.zip";
		doZip(files,zipPath);
		
		FileInputStream fs;
		byte[] Content=null;
			fs = new FileInputStream(zipPath);
			Content=new byte[fs.available()];
			fs.read(Content,0,Content.length);
			fs.close();
		
		return Content;
	}
	public void doZip(File[] files,String zipPath) throws Exception{
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
