package com.newland.beecode.service;


import org.springframework.web.multipart.MultipartFile;
import com.newland.beecode.exception.AppException;

/**
 * @author shaoxr:
 * @version 2011-5-13 下午03:09:26
 * 
 */
public interface TransactionalService {
	
	public void append(Long actNo, MultipartFile file, String bizNo)throws AppException;
	 
	 public long[] checkMarketingAct(Long actNo, Integer actStatus)throws AppException;
	 

}
