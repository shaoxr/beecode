package com.newland.beecode.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author shaoxr:
 * @version 2011-4-29 下午04:22:53
 * 
 */

public interface FileService {
	
	public void storeFile(MultipartFile file, String fileName) throws IOException;
	
	public FileReader getActFile(String fileName) throws FileNotFoundException;
	
	public String getTextContent(String fileName)throws IOException;
	
	public void genTextFile(String content,Long couponId);
	
	public String getPath(String fileName);
	
	public  byte[] getZIPByte(Long couponId)throws Exception;
	
	public void doZip(File[] files,String zipPath) throws Exception;
}
