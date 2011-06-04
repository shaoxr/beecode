package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.SmsSendList;

public interface SmsSendListService {
	
	public SmsSendList saveOrUpdate(SmsSendList smsSendList);
	
	public List<SmsSendList> findByLimit(Integer start,Integer end);
	
	public long countAll();

}
