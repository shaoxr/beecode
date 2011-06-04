package com.newland.beecode.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shaoxr:
 * @version 2011-5-29 下午10:10:59
 * 
 */
public class ThreadPoolFactory {
	
	public static ThreadPoolExecutor newThreadPool(int queueSize){
		return newThreadPool(3, 10, 20, TimeUnit. SECONDS ,  
					new ArrayBlockingQueue<Runnable>(queueSize), new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	public static ThreadPoolExecutor newThreadPool(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            RejectedExecutionHandler handler){
		return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,handler);
	}

}
