package com.newland.beecode.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.condition.CheckResult;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.FileService;
@Service("checkService")
public class CheckServiceImpl implements CheckService{
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private FileService fileService;
	@Override
	public CheckResult customerCheck(MultipartFile file,String fileName)throws AppException {
		StringBuffer tempError = new StringBuffer();
		CheckResult cr=new CheckResult();
		cr.setPass(true);
		try {
			this.fileService.storeFile(file,fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			if(sheet==null){
				cr.setPass(false);
				cr.setMessage(messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_SHEET_NOT_FIND, null, Locale.CHINA));
				return cr;
			}
			HSSFRow row;
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				row = sheet.getRow(j);
				HSSFCell name = row.getCell((short) i++);
				tempError.append("<tr><td>" + j + "</td><td>");
				if(name==null || name.toString().equals("")){
					tempError.append(messageSource.getMessage(ErrorsCode.BIZ_COUSTMER_NAME_NULL, null, Locale.CHINA));
					cr.setPass(false);
				}
				tempError.append("</td><td>");
				HSSFCell mobile = row.getCell((short) i++);
				if(mobile==null || mobile.toString().equals("")||mobile.toString().length()!=11){
					tempError.append(messageSource.getMessage(ErrorsCode.BIZ_COUSTMER_MOBILE_ERROR, null, Locale.CHINA));
					cr.setPass(false);
				}
				tempError.append("</td></tr>");
			}
			if(cr.isPass()){
				cr.setMessage(messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_PASS, new Object[]{sheet.getLastRowNum()}, Locale.CHINA));
				return cr;
			}
			cr.setMessage(tempError.toString());
			return cr;
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		}catch (RuntimeException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_CHECK_ERROR,"");
		}
	}
	public String customerCheck(String fileName)throws AppException{
		return null;
	}
	@Override
	public List<Customer> getCustomers(String fileName) throws AppException {
		POIFSFileSystem fs;
		List<Customer> list=new ArrayList<Customer>();
		try {
			fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			HSSFRow row;
			Customer customer;
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				customer=new Customer();
				row = sheet.getRow(j);
				HSSFCell name = row.getCell((short) i++);
				HSSFCell mobile = row.getCell((short) i++);
				customer.setName(name.toString());
				customer.setMobile(mobile.toString());
				list.add(customer);
				
			}
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_CHECK_ERROR,"");
		}
		return list;
	}


}
