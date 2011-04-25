package org.tempuri.service;

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
	public String[] sendMMS(String user,String password,String mobile,String title,byte[] mmsContent)throws Exception;
	
	public long getBalance(String user,String password,int sendType)throws Exception;

}
