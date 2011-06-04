package com.newland.beecode.service;


import java.util.List;

import com.newland.beecode.domain.RespStatus;

public interface RespStatusService {
	public List<RespStatus> findBySendListId(Long mmsSendListId);
	
}
