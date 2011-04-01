package com.newland.beecode.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.condition.CheckResult;
import com.newland.beecode.exception.AppException;

public interface CheckService {
	
	public CheckResult customerCheck(MultipartFile file,String fileName)throws AppException;
	
	public String customerCheck(String fileName)throws AppException;
	
	public List<Customer> getCustomers(String fileName)throws AppException;
	

}
