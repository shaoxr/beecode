package com.newland.beecode.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.dao.TerminalDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.Terminal;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.exception.ExcelException;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.PartnerService;
import com.newland.beecode.service.TerminalService;
@Service("checkService")
public class CheckServiceImpl implements CheckService{
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private FileService fileService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private TerminalDao terminalDao;
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 }
	public static boolean isIntegerOrFloat(String str){
		Pattern pattern = Pattern.compile("([0-9]+)|([0-9]+.[0-9]+)");	
		return pattern.matcher(str).matches(); 
	}
	private static final int NAME=0;
	
	private static final int MOBILE=1;
	
	private static final int ACCOUNT=2;
	
	private static final int EXCHANGE_NAME=3;
	
	private static final int EXCHANGE_AMOUNT=4;
	
	private static final int REBATE_RATE=5;
	
	private static final int BACK_AMOUNT=6;
	@Override
	public String checkCustomerFile(MultipartFile file, String fileName,Customer customer,MarketingAct act)throws ExcelException, AppException {
		StringBuffer tempError = new StringBuffer();
		try {
			this.fileService.storeFile(file,fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_SHEET_NOT_FIND, null, Locale.CHINA));
			}
			if(sheet.getLastRowNum()<1){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_BLANK, null, Locale.CHINA));
			}
			if(sheet.getLastRowNum()>5000){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_ROW_OVER, new Object[]{sheet.getLastRowNum()}, Locale.CHINA));
			}
			if(customer!=null){
				customer.setCount(sheet.getLastRowNum());
			}
			HSSFRow row;
			String nameError;
			String mobileError;
			String accountError;
			String exchangeNameError;
			String exchangeAmountError;
			String rebaterateError;
			String backAmountError;
			int errorCount=0;
			ArrayList<String> mobileList = new ArrayList<String>();
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				row = sheet.getRow(j);
				HSSFCell name = row.getCell(NAME);
				nameError="";
				if(name==null || name.toString().equals("")){
					nameError=messageSource.getMessage(ErrorsCode.BIZ_COUSTMER_NAME_NULL, null, Locale.CHINA);
				}
				HSSFCell mobile = row.getCell(MOBILE);
				mobileError="";
				if(mobile==null || mobile.toString().equals("")||mobile.toString().length()!=11 ||!isNumeric(mobile.toString())){
					mobileError=messageSource.getMessage(ErrorsCode.BIZ_COUSTMER_MOBILE_ERROR, null, Locale.CHINA);
				}
				accountError="";
				HSSFCell account = row.getCell(ACCOUNT);
				if(account!=null && !account.toString().equals("")){
					if(!isNumeric(account.toString())){
						accountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_ACCOUNT_NOT_NUMBER, null, Locale.CHINA);
					}else if((account.toString().length()!=16 && account.toString().length()!=19)){
						accountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_ACCOUNT_LENGTH_ERROR, null, Locale.CHINA);
					}
				}
				backAmountError="";
				exchangeNameError="";
				exchangeAmountError="";
				rebaterateError="";
				if(act.getImportType().equals(MarketingAct.IMPORT_TYPE_EXCEL)){
				  if(act.getBizNo().equals(Coupon.BIZ_TYPE_VOUCHER)){				
						HSSFCell backAmount = row.getCell(BACK_AMOUNT);
						if (backAmount == null || backAmount.toString().trim().equals("")) {
							backAmountError = messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_BACKAMOUNT_NULL, null,Locale.CHINA);
						} else {
							if (!isIntegerOrFloat(backAmount.toString())) {
								backAmountError = messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_BACKAMOUNT_ERROR,null, Locale.CHINA);
							}
						}
				  }
				  if(act.getBizNo().equals(Coupon.BIZ_TYPE_EXCHANGE)){
					
						HSSFCell exchangeName=row.getCell(EXCHANGE_NAME);
						if(exchangeName==null || exchangeName.toString().trim().equals("")){
							exchangeNameError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXCHANGE_NAME_NULL, null, Locale.CHINA);
						}
						HSSFCell exchangeAmount=row.getCell(EXCHANGE_AMOUNT);
						if(exchangeAmount==null ||exchangeAmount.toString().trim().equals("")){
							exchangeAmountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXCHANGE_AMOUNT_NULL, null, Locale.CHINA);
						}else if(!isIntegerOrFloat(exchangeAmount.toString())){
							exchangeAmountError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXCHANGE_AMOUNT_NOT_NUMBER, null, Locale.CHINA);
						}
				  }
				  if(act.getBizNo().equals(Coupon.BIZ_TYPE_DISCOUNT)){
					    HSSFCell rebaterate=row.getCell(REBATE_RATE);
					    if(rebaterate==null || rebaterate.toString().trim().equals("")){
					    	rebaterateError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_REBATE_RATE_NULL, null, Locale.CHINA);
					    }else if(!isIntegerOrFloat(rebaterate.toString())){
					    	rebaterateError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_REBATE_RATE_NOT_NUMBER, null, Locale.CHINA);
					    }else if(new Double(rebaterate.toString())>=1 ||new Double(rebaterate.toString())<=0){
					    	rebaterateError=messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_REBATE_RATE_ERROR, null, Locale.CHINA);
					    }
				  }
			   }
				if(nameError!="" || mobileError!="" ||accountError!="" || exchangeNameError!="" || exchangeAmountError!="" || rebaterateError!="" || backAmountError!=""){
					errorCount++;
					tempError.append("<tr><td>" + (j+1) + "</td><td>");
					tempError.append(nameError);
					tempError.append("</td><td>");
					tempError.append(mobileError);
					tempError.append("</td><td>");
					tempError.append(accountError);
					tempError.append("</td><td>");
					tempError.append(exchangeNameError);
					tempError.append("</td><td>");
					tempError.append(exchangeAmountError);
					tempError.append("</td><td>");
					tempError.append(rebaterateError);
					tempError.append("</td><td>");
					tempError.append(backAmountError);
					tempError.append("</td></tr>");
				}
				if(errorCount>=20){
					tempError.append("<tr><td>..........</td><td>");
					tempError.append("..........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td></td></tr>");
					break;
				}
				/*if(nameError=="" && mobileError==""){
					if(mobileList.contains(mobile.toString())){
						
						errorCount++;
						tempError.append("<tr><td>" + (j+1) + "</td><td>");
						tempError.append(messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXITS, new Object[]{mobile.toString()}, Locale.CHINA));
						tempError.append("</td><td></td><td></td><td></td></tr>");
						//return messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_EXITS, new Object[]{mobile.toString()}, Locale.CHINA);
					}
					mobileList.add( mobile.toString());
				}*/
			}
			if(errorCount==0){
				return messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_PASS, new Object[]{sheet.getLastRowNum()}, Locale.CHINA);
				
			}
			throw new ExcelException("",tempError.toString());
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		}
	}
	@Override
	public List<Customer> getCustomer(String fileName)throws ExcelException,AppException {
		try {
			//this.checkCustomerFile(file, fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			HSSFRow row;
			List<Customer> list=new ArrayList<Customer>();
			Customer customer;
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				customer=new Customer();
				row = sheet.getRow(j);
				HSSFCell name = row.getCell(i++);
				HSSFCell mobile = row.getCell(i++);
				HSSFCell account = row.getCell(i++);
				HSSFCell exchangeName = row.getCell(i++);
				HSSFCell exchangeAmount = row.getCell(i++);
				HSSFCell rabaterate = row.getCell(i++);
				HSSFCell backAmount = row.getCell(i++);
				
				customer.setName(name.toString());
				customer.setMobile(mobile.toString());
				if(account==null ||account.toString().equals("")){
					customer.setAccount("****************");
				}else{
					customer.setAccount(account.toString());
				}
				if(exchangeName!=null && !exchangeName.toString().equals("")){
					
					customer.setExchangeName(exchangeName.toString());
				}
				if(exchangeAmount!=null && !exchangeAmount.toString().trim().equals("")){
					customer.setExchangeAmount(new BigDecimal(exchangeAmount.toString()));
				}
				if(rabaterate!=null && !rabaterate.toString().trim().equals("")){
					customer.setRebaterate(new BigDecimal(rabaterate.toString()));
				}
				if(backAmount!=null && !backAmount.toString().trim().equals("")){
					customer.setBackAmount(new BigDecimal(backAmount.toString()));
				}
				
				list.add(customer);
				
			}
			return list;
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_CUSTOMER_FILE_UPLOAD_ERROR,"");
		}
	}
	
	@Override
	@Transactional
	public Set<Terminal> partnerCheck(MultipartFile file, String fileName)
			throws AppException {
		StringBuffer tempError = new StringBuffer();
		Set<Terminal> terminals=new HashSet<Terminal>();
		try {
			this.fileService.storeFile(file,fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("partner");
			HSSFRow row;
			String terminalNameError;
			String terminalNoError;
			long errorCount=0;
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_PARTNER_SHEET_NOT_FOUND, null, Locale.CHINA));
			}
			if(sheet.getLastRowNum()<1){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_BLANK, null, Locale.CHINA));
			}
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				row = sheet.getRow(j);
				HSSFCell name = row.getCell(i++);
				terminalNameError="";
				if(name==null || name.toString().equals("")){
					terminalNameError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_NAME_NULL, null, Locale.CHINA);
				}
				HSSFCell terminalNo = row.getCell(i++);
				terminalNoError="";
				Terminal terminal= null;
				if(terminalNo==null || terminalNo.toString().equals("")){
					terminalNoError=messageSource.getMessage(ErrorsCode.BIZ_TERMINAL_EXCEL_TERMINALNO_ERROR, null, Locale.CHINA);
				}else{
					terminal=this.terminalService.findByTerminalNo(terminalNo.toString());
					if(terminal==null){
						terminalNoError=messageSource.getMessage(ErrorsCode.BIZ_TERMINAL_EXCEL_NOT_EXITS, null, Locale.CHINA);
					}else{
						terminals.add(terminal);
					}
				}
				
				if(terminalNoError!="" || terminalNameError!="" ){
					errorCount++;
					tempError.append("<tr><td>" + (j+1) + "</td><td>");
					tempError.append(terminalNameError);
					tempError.append("</td><td>");
					tempError.append(terminalNoError);
					tempError.append("</td></tr>");
				}
				if(errorCount>=20){
					tempError.append("<tr><td>......</td><td>");
					tempError.append("..........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td></tr>");
					break;
				}
				
				//tempError.append("</td></tr>");
			}
			if(errorCount==0){
				//return messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_PASS, new Object[]{sheet.getLastRowNum()}, Locale.CHINA);
				return terminals;
			}
			throw new ExcelException("",tempError.toString());
			
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_PARTNER_EXCEL_FILE_UPLOAD_ERROR,"");
		} catch (IllegalArgumentException e) {
			throw new AppException(ErrorsCode.BIZ_PARTNER_EXCEL_FILE_UPLOAD_ERROR,"");
		}
	}
	@Override
	public List<Coupon> getCouponsByRespStatus(MultipartFile file, Integer type)
			throws AppException {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet=null;
			String sheetName=null;
			if(type.equals(SendList.MS_TYPE_MMS)){
				sheetName=SendList.MMS_RESP_FILE_NAME;
			}else{
				sheetName=SendList.SMS_RESP_FILE_NAME;
			}
			sheet=wb.getSheet(sheetName);
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_CUSTOMER_SHEET_NOT_FIND, new Object[]{sheetName}, Locale.CHINA));
			}
			HSSFRow row;
			List<Coupon> coupons=new ArrayList<Coupon>();
			Coupon coupon =null;
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				coupon =new Coupon();
				row = sheet.getRow(j);
				HSSFCell couponId = row.getCell(i++);
				coupon.setCouponId(new Long(couponId.toString()));
				HSSFCell status = row.getCell(i++);
				coupon.setMmsStatus(new Integer(status.toString()));
				coupon.setSmsStatus(new Integer(status.toString()));
				HSSFCell statusDesc = row.getCell(i++);
				coupon.setMmsDesc(statusDesc.toString());
				coupon.setSmsDesc(statusDesc.toString());
				coupons.add(coupon);
				
			}
			return coupons;
		} catch (RuntimeException e) {
			throw new AppException(ErrorsCode.BIZ_RESP_STATUS_ERROR,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_RESP_STATUS_ERROR,"",e);
		}
	}
	@Override
	public void checkCodeCheck(MarketingAct act) throws AppException {
		if(act.getBindCard().equals(MarketingAct.BIND_CARD_NO)){
			return ;
		}
		if(act.getCheckCode()==null ||act.getCheckCode().trim().equals("")){
			throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CHECK_CODE_NOT_NULL,"");
		}
		String[] strs=act.getCheckCode().split(MarketingAct.NEW_LINE);
		StringBuffer sb=new StringBuffer();
		Pattern pattern = Pattern.compile("[0-9[*]]{6}"); 
		Pattern pattern_ = Pattern.compile("[0-9]+[*]*");
		int i=0;
		for(String bin:strs){
			if(!pattern.matcher(bin).matches()){
				throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CHECK_CODE_FORMAT_ERROR,"");
			}
			if(!pattern_.matcher(bin).matches()){
				throw new AppException(ErrorsCode.BIZ_MARKETINGACT_CHECK_CODE_ORDER_ERROR,"");
			}
			if(i==strs.length-1){
				sb.append(bin);
			}else{
				sb.append(bin+MarketingAct.CHECK_CODE_REGEX);
			}
			i++;
			
		}
		//act.setCheckCode(sb.toString());
		
	}
	@Override
	public List<Customer> newland(File file) {
		List<String> mobiles=null;
		try {
			//this.checkCustomerFile(file, fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("customer");
			HSSFRow row;
			List<Customer> list=new ArrayList<Customer>();
			Customer customer;
			mobiles=new ArrayList<String>();
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				customer=new Customer();
				row = sheet.getRow(j);
				HSSFCell mobile = row.getCell((short) i++);
				mobile.setCellType(HSSFCell.CELL_TYPE_STRING);
				//customer.setMobile(mobile.toString());
				//list.add(customer);
				if(mobile!=null && mobile.toString().length()==11 && mobile.toString().indexOf("1")==0){
					if(!mobiles.contains(mobile.toString())){
						customer.setMobile(mobile.toString());
						mobiles.add(mobile.toString());
						list.add(customer);
					}
					
				}
				
			}
			System.out.println(list.size()+">>>>>>>>");
			POIFSFileSystem fs2 = new POIFSFileSystem(new FileInputStream("e:/name12.xls"));
			HSSFWorkbook wb2 = new HSSFWorkbook(fs2);
			HSSFSheet sheet2 = wb2.getSheet("customer");
			HSSFRow row2;
			for (int j = 0; j <= sheet2.getLastRowNum(); j++) {
				int i=0;
				customer=new Customer();
				row2 = sheet2.getRow(j);
				HSSFCell mobile = row2.getCell((short) i++);
				mobile.setCellType(HSSFCell.CELL_TYPE_STRING);
				//customer.setMobile(mobile.toString());
				//list.add(customer);
				if(mobile!=null && mobile.toString().length()==11 && mobile.toString().indexOf("1")==0){
					System.out.println(mobile.toString()+"aaaaaa"+j);
					if(!mobiles.contains(mobile.toString())){
						customer.setMobile(mobile.toString());
						mobiles.add(mobile.toString());
						list.add(customer);
					}
					
				}
				
			}
			
			System.out.println(list.size()+""+mobiles.size());
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Set<Terminal> partnerImport(MultipartFile file,String fileName) throws AppException {
		StringBuffer tempError = new StringBuffer();
		Set<Terminal> terminals=new HashSet<Terminal>();
		try {
			this.fileService.storeFile(file,fileName);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.fileService.getPath(fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("partner");
			HSSFRow row;
			String terminalNameError;
			String terminalNoError;
			String partnerNameError;
			String partnerNoError;
			long errorCount=0;
			if(sheet==null){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_PARTNER_SHEET_NOT_FOUND, null, Locale.CHINA));
			}
			if(sheet.getLastRowNum()<1){
				throw new ExcelException("",messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_BLANK, null, Locale.CHINA));
			}
			for (int j = 1; j <= sheet.getLastRowNum(); j++) {
				int i=0;
				row = sheet.getRow(j);
				HSSFCell partnerName = row.getCell(i++);
				partnerNameError="";
				if(partnerName==null || partnerName.toString().equals("")){
					partnerNameError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_NAME_NULL, null, Locale.CHINA);
				}
				HSSFCell partnerNo = row.getCell(i++);
				partnerNoError="";
				if(partnerNo==null || partnerNo.toString().equals("")){
					partnerNoError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_PARTNERNO_ERROR, null, Locale.CHINA);
				}
				Partner partner=this.partnerService.findByPartnerNo(partnerNo.toString());
				if(partner==null){
					partnerNoError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_NOT_EXITS, null, Locale.CHINA);
				}
				HSSFCell name = row.getCell(i++);
				terminalNameError="";
				List<Terminal> _terminals ;
				if(name==null || name.toString().equals("")){
					terminalNameError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_NAME_NULL, null, Locale.CHINA);
				}else{
					_terminals=this.terminalDao.find("from Terminal t where t.name=?", name.toString());
					if(_terminals.size()>0){
						terminalNameError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_TERMINAL_NAME_EXITS, null, Locale.CHINA);
					}
				}
				
				HSSFCell terminalNo = row.getCell(i++);
				terminalNoError="";
				Terminal terminal= null;
				if(terminalNo==null || terminalNo.toString().equals("")){
					terminalNoError=messageSource.getMessage(ErrorsCode.BIZ_TERMINAL_EXCEL_TERMINALNO_ERROR, null, Locale.CHINA);
				}else{
					terminal=this.terminalService.findByTerminalNo(terminalNo.toString());
					if(terminal!=null){
						terminalNoError=messageSource.getMessage(ErrorsCode.BIZ_PARTNER_TERMINAL_NO_EXITS, null, Locale.CHINA);
					}
				}
				
				if(terminalNoError!="" || terminalNameError!="" || partnerNameError!="" || partnerNoError!=""){
					errorCount++;
					tempError.append("<tr><td>" + (j+1) + "</td><td>");
					tempError.append(partnerNameError);
					tempError.append("</td><td>");
					tempError.append(partnerNoError);
					tempError.append("</td><td>");
					tempError.append(terminalNameError);
					tempError.append("</td><td>");
					tempError.append(terminalNoError);
					tempError.append("</td></tr>");
				}else{
					Terminal terminal_=new Terminal();
					terminal_.setName(name.toString());
					terminal_.setTerminalNo(terminalNo.toString());
					terminal_.setPartner(partner);
					terminals.add(terminal_);
				}
				if(errorCount>=20){
					tempError.append("<tr><td>......</td><td>");
					tempError.append("..........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td><td>");
					tempError.append("...........");
					tempError.append("</td></tr>");
					break;
				}
				
				//tempError.append("</td></tr>");
			}
			if(errorCount==0){
				//return messageSource.getMessage(ErrorsCode.BIZ_PARTNER_EXCEL_PASS, new Object[]{sheet.getLastRowNum()}, Locale.CHINA);
				return terminals;
			}
			throw new ExcelException("",tempError.toString());
			
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_PARTNER_EXCEL_FILE_UPLOAD_ERROR,"");
		} catch (IllegalArgumentException e) {
			throw new AppException(ErrorsCode.BIZ_PARTNER_EXCEL_FILE_UPLOAD_ERROR,"");
		}
	}

}
