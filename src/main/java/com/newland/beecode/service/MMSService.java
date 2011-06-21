package com.newland.beecode.service;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.task.SendParam;

public interface MMSService {
	
	/**
	 * 
	 * @param user
	 * @param password
	 * @param mobile
	 * @param title
	 * @param mmsContent
	 * @return
	 */
	public String[] sendMMS(SendParam mp) throws Exception;
	
	public long getBalance(String user,String password,int sendType)throws Exception;
	
	public String sendMMSByMontnets(SendParam mp)throws Exception;
	
	public String getBalanceByMontnets()throws AppException;

}
