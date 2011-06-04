package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.MsStatus;
import com.newland.beecode.domain.SendList;

/**
 * @author shaoxr:
 * @version 2011-5-16 下午05:53:01
 * 
 */
public interface SendListService {
	
	public SendList saveOrUpdate(SendList mmsSendList);
	
	public List<SendList> findByLimit(Integer start,Integer end);
	
	public long countAll();
	
	public SendList findById(Long id);
	/**
	 * 发送结束更新状态
	 * @param id
	 */
	public void sendOver(Long id);
	
	 public MsStatus findSendCount();

}
