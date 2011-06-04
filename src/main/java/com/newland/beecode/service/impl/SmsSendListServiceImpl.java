package com.newland.beecode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.SmsSendListDao;
import com.newland.beecode.domain.SmsSendList;
import com.newland.beecode.service.SmsSendListService;

@Service(value="smsSendListService")
public class SmsSendListServiceImpl implements SmsSendListService{
	@Autowired
    private SmsSendListDao smsSendListDao;
	@Override
	public SmsSendList saveOrUpdate(SmsSendList smsSendList) {
		return smsSendListDao.saveOrUpdate(smsSendList);
		
	}
	@Override
	public List<SmsSendList> findByLimit(Integer start, Integer end) {
		return this.smsSendListDao.findListByQuery("from SmsSendList m order by m.submitTime ", start, end);
	}
	@Override
	public long countAll() {
		return this.smsSendListDao.findLong("select count(*) from SmsSendList");
	}

}
