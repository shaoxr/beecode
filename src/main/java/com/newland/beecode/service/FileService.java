package com.newland.beecode.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.exception.AppException;

/**
 * @author shaoxr:
 * @version 2011-4-29 下午04:22:53
 * 
 */

public interface FileService {
	
	public void storeFile(MultipartFile file, String fileName)  throws AppException;
	
	public FileReader getActFile(String fileName)  throws AppException;
	
	public String getTextContent(String fileName,String dir) throws AppException;
	public String getTextContent(String fileName) throws AppException;
	
	public void genTextFile(String content,Long couponId) throws AppException;
	
	public String getPath(String fileName);
	
	public  byte[] getZIPByte(Long couponId,String dir) throws AppException;
	public  byte[] getZIPByte(Long couponId) throws AppException;
	
	public String getBase64Tms(Long couponId,String dir)throws AppException;
	
	public void genGetMMSzips(MarketingAct act,List<Coupon> coupon,HttpServletResponse response) throws AppException;
	
	public File getCustomerFile(String fileName) throws AppException;
	
	public String extractMms(MultipartFile file) throws AppException;
	
	public List<Coupon> getCoupon(String dir) throws AppException;
	
	public MarketingAct getActFile(String dir,Integer type) throws AppException;
	
	public String getImagePath();
	public void genGetSMSzips(MarketingAct act,List<Coupon> coupon,HttpServletResponse response) throws AppException;
	public String extractSms(MultipartFile file) throws AppException;
	
	public FileInputStream getRespStatus(SendList sendList,List<RespStatus> respStatuss)throws AppException;
	public List<Coupon> getCouponsByRespStatus(MarketingAct act,MultipartFile file, Integer type)throws AppException;

	public FileInputStream getActInfomation(MarketingAct act) throws AppException;
}
