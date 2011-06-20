package com.newland.beecode.task.service;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.task.SendParam;

/**
 * @author shaoxr:
 * @version 2011-5-30 下午09:03:59
 * 
 */
public interface SendInvokeService {
	
	public void sendRun(SendParam sp)throws Exception;
	
	public void sendOver(Long actNo ,Long sendListId);
	
	public Integer getMsType();

}
