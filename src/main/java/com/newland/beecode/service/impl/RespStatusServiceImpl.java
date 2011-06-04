package com.newland.beecode.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.RespStatusDao;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.service.RespStatusService;

@Service(value="respStatusService")
public class RespStatusServiceImpl implements RespStatusService {
	
	@Autowired
	private RespStatusDao respStatusDao;

	@Override
	public List<RespStatus> findBySendListId(Long mmsSendListId) {

		return this.respStatusDao.findByProperty("mmsSendListId", mmsSendListId);
	}


}
