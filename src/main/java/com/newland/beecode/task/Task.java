package com.newland.beecode.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newland.beecode.task.service.SendInvokeService;

/**
 * @author shaoxr:
 * @version 2011-5-30 下午09:52:30
 * 
 */
public class Task implements Runnable{
	private  Log logger = LogFactory.getLog(this.getClass());
	private SendInvokeService sendInvokeService;
	
	private SendParam sp;
	
	public Task(SendInvokeService sendInvokeService,SendParam sp){
		this.sendInvokeService=sendInvokeService;
		this.sp=sp;
	}

	@Override
	public void run() {
			try {
				this.sendInvokeService.sendRun(sp);
			} catch (Exception e) {
				logger.error("", e);
			}
	}

}
