package com.newland.beecode.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.MsStatus;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.exception.AppException;

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
	
	public void send(MultipartFile file,Integer msType)throws AppException;
	
	public MsStatus findSendCount();

}
