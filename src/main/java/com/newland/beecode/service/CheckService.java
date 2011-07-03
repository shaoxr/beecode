package com.newland.beecode.service;


import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ExcelException;

public interface CheckService {
	
	public String checkCustomerFile(MultipartFile file, String fileName,Customer customer,String bizNo)throws ExcelException, AppException;
	
	public List<Customer> getCustomer(String fileName)throws ExcelException,AppException;
	
	public Set<Partner> partnerCheck(MultipartFile file,String fileName) throws AppException;
	
	public List<Coupon> getCouponsByRespStatus(MultipartFile file,Integer type)throws AppException;
	
	public void checkCodeCheck(MarketingAct act)throws AppException;
	
	public List<Customer> newland(File file);
	
	
}
