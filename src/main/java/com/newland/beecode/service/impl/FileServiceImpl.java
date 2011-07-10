package com.newland.beecode.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import sun.misc.BASE64Encoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.intensoft.base.Dictionary;
import com.intensoft.formater.dao.DictViewDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.Terminal;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
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
	
	@Autowired
	private DictViewDao dictViewDao;
	
	public static final String TEMP="temp/";
	
    public static final String TEXT_PATH="text/";
	
	public static final String IMAGE_PATH="images/";
	
	public static final String ZIP_PATH="zip/";
	
	public static final String SMIL_PATH="smil/";
	
	public static final String MMS_DOWN_TEMP="mms_down_temp/";
	
	public static final String TXT_REGEX_IN="::::";
	
	public static final String MMS_SEND_EXTRACT="mms_send_extract/";
	
	public static final String COUPONS_TXT="coupons.txt";
	
	public static final String MMS_ACT_TXT="mmsAct.txt";
	
	public static final String SMS_ACT_TXT="smsAct.txt";
	public static final String SMS_DOWN_TEMP="sms_down_temp/";
	public static final String SMS_SEND_EXTRACT="sms_send_extract/";
	
	public static final String encode="utf-8";
	
	
	public static final int BUFFER = 2048;
	public void storeFile(MultipartFile file, String fileName)  throws AppException {
		File nf = new File(baseService.getFilePath()+TEMP + fileName);
		if (nf.exists()) {
			nf.delete();
		}
		try {
			nf.createNewFile();
			FileOutputStream fos = new FileOutputStream(nf);
			fos.write(file.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
	}

	public FileReader getActFile(String fileName)  throws AppException{
		File file = new File(this.baseService.getFilePath()+TEMP + fileName);
		try {
			return new FileReader(file);
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
		}
		
		
	}
	public String getTextContent(String fileName,String dir) throws AppException{
		File file=null;
		if(dir==null){
			file = new File(this.baseService.getFilePath()+TEXT_PATH+fileName+".txt");
		}else{
			file = new File(dir+"/"+fileName+".txt");
		}
		  
	        BufferedReader reader = null;
	        StringBuffer sb=new StringBuffer();
	        try {
	            InputStreamReader read = new InputStreamReader (new FileInputStream(file),encode);
	            reader = new BufferedReader(read);
	            String tempString = null;
	            while ((tempString = reader.readLine()) != null) {
	            	sb.append(tempString);
	            }
	            reader.close();
	        } catch (IOException e) {
	        	throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                	throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e1);
	                }
	            }
	        }
	        return sb.toString();
	}
	public String getTextContent(String fileName) throws AppException{
		 return this.getTextContent(fileName, null);
	}
	
	public String getPath(String fileName){
		return this.baseService.getFilePath()+TEMP+fileName;
	}
	public void genTextFile(String content,Long couponId) throws AppException{
		FileOutputStream fos = null;  
        OutputStreamWriter osw = null;  
        try {  
        	File file=new File(this.baseService.getFilePath()+TEXT_PATH+couponId+".txt");
        	if(file.exists()){
        		file.delete();
        	}
            fos = new FileOutputStream(file);  
            osw = new OutputStreamWriter(fos, encode);  
            osw.write(content); 
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}finally{
			try {
				osw.close();
			} catch (IOException e) {
				throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
			}
		}
		
	}

	public  byte[] getZIPByte(Long couponId,String dir) throws AppException{
		    File[] files=new File[3];
		    if(dir==null){
		    	files[0]=new File(this.baseService.getFilePath()+TEXT_PATH+couponId+".txt");
		    	files[1]=new File(this.baseService.getFilePath()+IMAGE_PATH+couponId+".gif");
		    	files[2]=new File(this.baseService.getFilePath()+SMIL_PATH+"beecode.smil");
		    }else{
		    	files[0] = new File(dir+"/"+couponId+".txt");
		    	files[1]=new File(dir+"/"+couponId+".gif");
		    	files[2]=new File(this.baseService.getFilePath()+SMIL_PATH+"beecode.smil");
		    }
			
			return this.getZIPByte(files);
	}
	private byte[] getZIPByte(File[] files)throws AppException{
		try {
		String zipPath=this.baseService.getFilePath()+ZIP_PATH+"beecode.zip";
		doMMSZip(files,zipPath);
		
		FileInputStream fs;
		byte[] Content=null;
			fs = new FileInputStream(zipPath);
			Content=new byte[fs.available()];
			fs.read(Content,0,Content.length);
			fs.close();
		
		return Content;
    } catch (FileNotFoundException e) {
	   throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
    } catch (IOException e) {
	   throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
    }
	}
	@Override
	public  byte[] getZIPByte(Long couponId) throws AppException{
			File files[] = {new File(this.baseService.getFilePath()+TEXT_PATH+"newland"+".txt"),new File(this.baseService.getFilePath()+IMAGE_PATH+couponId+".gif"),new File(this.baseService.getFilePath()+SMIL_PATH+"beecode.smil")};
			return this.getZIPByte(files);
	}
	
	private void doMMSZip(File[] files,String zipPath) throws AppException{
			try {
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
			} catch (FileNotFoundException e) {
				throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
			} catch (IOException e) {
				throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
			}
	}
	@Override
	public String getBase64Tms(Long couponId, String dir) throws AppException {
		File[] files=new File[3];
	    if(dir==null){
	    	files[0]=new File(this.baseService.getFilePath()+SMIL_PATH+"tms.smil");
	    	files[1]=new File(this.baseService.getFilePath()+IMAGE_PATH+couponId+".gif");
	    	files[2]=new File(this.baseService.getFilePath()+TEXT_PATH+couponId+".txt");
	    }else{
	    	files[0]=new File(this.baseService.getFilePath()+SMIL_PATH+"tms.smil");
	    	files[1]=new File(dir+"/"+couponId+".gif");
	    	files[2] = new File(dir+"/"+couponId+".txt");
	    }
	    BASE64Encoder encode = new BASE64Encoder();
	    return encode.encode(getTmsByte(files));
	}
	private byte[] getTmsByte(File[] files)throws AppException{ 
		ByteArrayOutputStream fos = new ByteArrayOutputStream(); 
		try {
			String title="shaoxr";
			
			FileInputStream fs;
			int totalLen=0;
			for(File file:files ){
				totalLen=totalLen+(int)file.length();
			}
			fos.write(0xA1);
			fos.write(0x64);
			fos.write(genByte(totalLen,4));
			fos.write(3);
			fos.write(genByte(title.length(),2));
			fos.write(title.getBytes());
			
			
			for(File file:files ){
				fs = new FileInputStream(file);
				String fileName=null;
				if(file.getName().indexOf(".txt")!=-1){
					fileName="00.txt";
				}else if(file.getName().indexOf(".gif")!=-1){
					fileName="00.gif";
				}else{
					fileName=file.getName();
				}
				byte[] Content=new byte[fs.available()];
				fs.read(Content,0,Content.length);
				fs.close();
				fos.write(fileName.length());
				fos.write(fileName.getBytes());
				fos.write(genByte((int)file.length(),3));
				fos.write(Content);
			}
			//FileOutputStream fo=new FileOutputStream(this.baseService.getFilePath()+SMIL_PATH+"test.tms");
			//fos.writeTo(fo);
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
		return fos.toByteArray();
	}
	private static byte[] genByte(int i,int len){
		byte[] bytes=new byte[len];
		for(int j=0;j<len;j++){
			bytes[j]=(byte)(i>>(8*(len-1-j)) & 0xff);
		}
		return bytes;
	}
	@Override
	public File getCustomerFile(String fileName)  throws AppException {
		return new File(baseService.getFilePath()+TEMP + fileName);
	}

	@Override
	public void genGetMMSzips(MarketingAct act,List<Coupon> coupons,HttpServletResponse response)  throws AppException {
		
		try {
			ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
			/**
			 * 客户手机类表
			 */
			String listFileName=COUPONS_TXT;
			String listFilePath=this.baseService.getFilePath()+MMS_DOWN_TEMP+listFileName;
			FileWriter fw=new FileWriter(listFilePath);
				for(Coupon coupon:coupons){
					fw.write(coupon.getAcctMobile()+TXT_REGEX_IN+coupon.getCouponId());
					fw.write("\r\n");
				}
				fw.close();
			
			BufferedInputStream bufStr = null;
			byte data[] = new byte[BUFFER];
			
			FileInputStream fi = new FileInputStream(listFilePath);
			bufStr = new BufferedInputStream(fi, BUFFER);
			ZipEntry entry = new ZipEntry(listFileName);
			out.putNextEntry(entry);
			int count;
			while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bufStr.close();
			
			/**
			 * 活动文件
			 */
			
			String mmsTitle=MMS_ACT_TXT;
			String mmsTitlePath=this.baseService.getFilePath()+MMS_DOWN_TEMP+mmsTitle;
			fw=new FileWriter(mmsTitlePath);
			    fw.write(act.getActNo().toString());
			    fw.write("\r\n");
			    fw.write(act.getActName());
			    fw.write("\r\n");
			    fw.write(act.getMmsTitle());
				fw.close();
			fi = new FileInputStream(mmsTitlePath);
			bufStr = new BufferedInputStream(fi, BUFFER);
			entry = new ZipEntry(mmsTitle);
			out.putNextEntry(entry);
			while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bufStr.close();
			
			/**
			 * 彩信图片、内容
			 */
			
			for(Coupon coupon:coupons){ 
				String[] strs={coupon.getCouponId()+".gif",coupon.getCouponId()+".txt"};
				for(String str:strs){
					String filePath=null;
					if(str.indexOf(".gif")!=-1){
						filePath=this.baseService.getFilePath()+IMAGE_PATH+str;
					}else{
						filePath=this.baseService.getFilePath()+TEXT_PATH+str;
					}
					
					fi = new FileInputStream(filePath);
					bufStr = new BufferedInputStream(fi, BUFFER);
					entry = new ZipEntry(str);
					out.putNextEntry(entry);
					while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
						out.write(data, 0, count);
					}
					bufStr.close();
				}
				
			}
			out.close();
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
		
	}
    /**
     * 解压批量彩信zip
     */
	@Override
	public String extractMms(MultipartFile file) throws AppException {
		System.out.println("extractMms");
		String path=this.baseService.getFilePath()+MMS_SEND_EXTRACT +this.getRandomPath();
		this.extract(file, path);
		return path;
	}

	@Override
	public List<Coupon> getCoupon(String dir)  throws AppException {
		File file =new File(dir+"/"+COUPONS_TXT);
		List<Coupon> coupons=new ArrayList<Coupon>();
		  BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            Coupon coupon=null;
	            while ((tempString = reader.readLine()) != null) {
	            	String [] strs=tempString.split(TXT_REGEX_IN);
	            	coupon=new Coupon();
	            	coupon.setAcctMobile(strs[0]);
	            	coupon.setCouponId(new Long(strs[1]));
	            	coupons.add(coupon);
	            }
	            reader.close();
	        } catch (IOException e) {
	        	throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                	throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e1);
	                }
	            }
	        }
	        return coupons;
	}

	@Override
	public MarketingAct getActFile(String dir,Integer type) throws AppException{
		File file=null;
		if(type.equals(SendList.MS_TYPE_MMS)){
			file =new File(dir+"/"+MMS_ACT_TXT);
			if(!file.exists()){
				throw new AppException(ErrorsCode.BIZ_MMS_SEND_ACT_FILE_NOT_FOUND,"");
			}
		}else if(type.equals(SendList.MS_TYPE_SMS)) {
			file =new File(dir+"/"+SMS_ACT_TXT);
			if(!file.exists()){
				throw new AppException(ErrorsCode.BIZ_SMS_SEND_ACT_FILE_NOT_FOUND,"");
			}
		}
		return this.getActFile(file);
	
	}
	private MarketingAct getActFile(File file)throws AppException{
		 MarketingAct act=new MarketingAct();
		 BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            /**
	             * 
	             */
	            tempString = reader.readLine();
	            act.setActNo(new Long(tempString));
	            /**
	             * 
	             */
	            tempString = reader.readLine();
	            act.setActName(tempString);
	            /**
	             * 
	             */
	            tempString = reader.readLine();
	            act.setMmsTitle(tempString);
	            reader.close();
	        } catch (IOException e) {
	        	throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
	        	
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                	throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e1);
	                }
	                }
	            }
	        return act;
	}

	@Override
	public String getImagePath() {
		return this.baseService.getFilePath()+IMAGE_PATH;
	}
	@Override
	public void genGetSMSzips(MarketingAct act,List<Coupon> coupons,HttpServletResponse response)  throws AppException {
		
		try {
			ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
			/**
			 * 客户手机类表
			 */
			String listFileName=COUPONS_TXT;
			String listFilePath=this.baseService.getFilePath()+SMS_DOWN_TEMP+listFileName;
			FileWriter fw=new FileWriter(listFilePath);
				for(Coupon coupon:coupons){
					fw.write(coupon.getAcctMobile()+TXT_REGEX_IN+coupon.getCouponId());
					fw.write("\r\n");
				}
				fw.close();
			
			BufferedInputStream bufStr = null;
			byte data[] = new byte[BUFFER];
			
			FileInputStream fi = new FileInputStream(listFilePath);
			bufStr = new BufferedInputStream(fi, BUFFER);
			ZipEntry entry = new ZipEntry(listFileName);
			out.putNextEntry(entry);
			int count;
			while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bufStr.close();
			
			/**
			 * 短信标题文件
			 */
			
			String smsTitle=SMS_ACT_TXT;
			String smsTitlePath=this.baseService.getFilePath()+SMS_DOWN_TEMP+smsTitle;
			fw=new FileWriter(smsTitlePath);
			fw.write(act.getActNo().toString());
		    fw.write("\r\n");
		    fw.write(act.getActName());
		    fw.write("\r\n");
		    fw.write(act.getMmsTitle());
			fw.close();
			fi = new FileInputStream(smsTitlePath);
			bufStr = new BufferedInputStream(fi, BUFFER);
			entry = new ZipEntry(smsTitle);
			out.putNextEntry(entry);
			while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bufStr.close();
			
			/**
			 * 短信内容
			 */
			for(Coupon coupon:coupons){ 
				String[] strs={coupon.getCouponId()+".txt"};
				for(String str:strs){
					String filePath=this.baseService.getFilePath()+TEXT_PATH+str;;
					fi = new FileInputStream(filePath);
					bufStr = new BufferedInputStream(fi, BUFFER);
					entry = new ZipEntry(str);
					out.putNextEntry(entry);
					while ((count = bufStr.read(data, 0, BUFFER)) != -1) {
						out.write(data, 0, count);
					}
					bufStr.close();
				}
			}
			out.close();
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
		
	}
	@Override
	public String extractSms(MultipartFile file) throws AppException {
		System.out.println("extractSms");
		String path=this.baseService.getFilePath()+SMS_SEND_EXTRACT + this.getRandomPath();
		extract(file,path);
		return path;
	}
	
	private void extract(MultipartFile file,String path)throws AppException{
		File dir;
		try {
				ZipInputStream in = new ZipInputStream(file.getInputStream());
				dir= new File(path);
				dir.mkdir();
				ZipEntry entry = null;
				while ((entry = in.getNextEntry()) != null) {
					String entryName = entry.getName();
					OutputStream os = new FileOutputStream(
							dir.getAbsolutePath()+"/" + entryName);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						os.write(buf, 0, len);
					}
					os.close();
					in.closeEntry();
				}
		} catch (FileNotFoundException e) {
			throw new AppException(ErrorsCode.BIZ_IO_FILE_NOT_FOUND,"",e);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
	}
	private synchronized String getRandomPath(){
		System.out.println(">>>>>>>>>>gen file name");
		return System.currentTimeMillis()+"";
	}
	public FileInputStream getRespStatus(SendList sendList,List<RespStatus> respStatuss)throws AppException{
		FileInputStream fis=null;
		File file=null;
		try {
			file=new File(this.baseService.getFilePath()+ZIP_PATH+getRandomPath()+".txt");
			FileWriter fw=new FileWriter(file);
			fw.write(sendList.getMsType().toString());
			fw.write("\r\n");
			fw.write(sendList.getActNo().toString());
			fw.write("\r\n");
			for(RespStatus respStatus:respStatuss){
				fw.write(respStatus.getCouponId()+TXT_REGEX_IN+respStatus.getRespStatus()+TXT_REGEX_IN+respStatus.getRespDesc());
				fw.write("\r\n");
			}
			fw.close();
			fis= new FileInputStream(file);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
		return fis;
	}

	@Override
	public FileInputStream getActInfomation(MarketingAct act)throws AppException{
		FileInputStream fis=null;
		File file=null;
		
		try {
			file=new File(this.baseService.getFilePath()+TEMP+getRandomPath()+".doc");
			FileWriter fw=new FileWriter(file);
			fw.write("活动类别："+act.getMarketingCatalog().getCatalogName());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.write("活动标题："+act.getActName());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.write("开始时间："+act.getStartDate());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.write("截止时间："+act.getEndDate());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.write("活动详情："+act.getActDetail());
			fw.write("\r\n");
			fw.write("\r\n");
			Dictionary dictionary = (Dictionary) dictViewDao.findUnique("from Dictionary where className=? and key=?", "BIND_CARD",act.getBindCard());
			fw.write("卡检查  ："+dictionary.getValue());
			fw.write("\r\n");
			fw.write("\r\n");
			if(act.getBindCard().equals(MarketingAct.BIND_CARD_YES)){
				fw.write("卡规则  :"+act.getCheckCode());
				fw.write("\r\n");
				fw.write("\r\n");
			}
			
			dictionary = (Dictionary) dictViewDao.findUnique("from Dictionary where className=? and key=?", "BUSINESS_TYPE",act.getBizNo());
			fw.write("礼券类型 :"+dictionary.getValue());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.write("可用次数："+act.getTimes());
			fw.write("\r\n");
			fw.write("\r\n");
			dictionary = (Dictionary) dictViewDao.findUnique("from Dictionary where className=? and key=?", "IMPORT_TYPE",act.getImportType().toString());
			fw.write("导入类型："+dictionary.getValue());
			fw.write("\r\n");
			fw.write("\r\n");
			if(act.getImportType().equals(MarketingAct.IMPORT_TYPE_GLOBAL)){
				if(act.getBizNo().equals(Coupon.BIZ_TYPE_EXCHANGE)){
					fw.write("礼券价值："+act.getAmount());
					fw.write("\r\n");
					fw.write("\r\n");
					fw.write("兑换物品："+act.getExchangeName());
					fw.write("\r\n");
					fw.write("\r\n");
				}
				if(act.getBizNo().equals(Coupon.BIZ_TYPE_DISCOUNT)){
					fw.write("折扣率   ："+act.getRebateRate());
					fw.write("\r\n");
					fw.write("\r\n");
					fw.write("上限额度："+act.getMaxAmount());
					fw.write("\r\n");
					fw.write("\r\n");
				}
				if(act.getBizNo().equals(Coupon.BIZ_TYPE_VOUCHER)){
					fw.write("返还金额："+act.getBackRate());
					fw.write("\r\n");
					fw.write("\r\n");
				}
			}else{
				if(act.getBizNo().equals(Coupon.BIZ_TYPE_DISCOUNT)){
					fw.write("上限额度："+act.getMaxAmount());
					fw.write("\r\n");
					fw.write("\r\n");
				}
			}
			
			dictionary = (Dictionary) dictViewDao.findUnique("from Dictionary where className=? and key=?", "ACT_STATUS",String.valueOf(act.getActStatus()));
			fw.write("活动状态: "+dictionary.getValue());
			fw.write("\r\n");
			fw.write("\r\n");
			
			fw.write("合作商户: ");
			int i=0;
			for(Terminal terminal:act.getTerminals()){
				if(i==0){
					fw.write(terminal.getAllName());
				}else{
					fw.write("          "+terminal.getAllName());
				}
				
				fw.write("\r\n");
				i++;
			}
			fw.write("\r\n");
			fw.write("发送标题："+act.getMmsTitle());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.write("发送内容：\r\n"+act.getMmsTemplate());
			fw.write("\r\n");
			fw.write("\r\n");
			fw.close();
			fis= new FileInputStream(file);
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}
		return fis;
	}
	
	@Override
	public List<Coupon> getCouponsByRespStatus(MarketingAct act,MultipartFile file, Integer type)
			throws AppException {
		BufferedReader reader = null;
		InputStreamReader isr=null;
		List<Coupon> coupons=new ArrayList<Coupon>();
		try {
			isr=new InputStreamReader(file.getInputStream());
			reader = new BufferedReader(isr);
			String readerStr=null;
			Integer readType=new Integer(reader.readLine());
			if(!readType.equals(type)){
				throw new AppException(ErrorsCode.BIZ_MMS_RESP_FILE_ERROR,"");
			}
			act.setActNo(new Long(reader.readLine()));
			Coupon coupon =null;
			while((readerStr=reader.readLine())!=null){
				coupon=new Coupon();
				String[] str=readerStr.split(TXT_REGEX_IN);
				coupon.setCouponId(new Long(str[0]));
				coupon.setMmsStatus(new Integer(str[1]));
				coupon.setSmsStatus(new Integer(str[1]));
				coupon.setMmsDesc(str[2]);
				coupon.setSmsDesc(str[2]);
				coupons.add(coupon);
			}
			
		} catch (IOException e) {
			throw new AppException(ErrorsCode.BIZ_IO_EXCEPTION,"",e);
		}finally{
			try {
				reader.close();
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return coupons;
	}
}
