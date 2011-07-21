package com.newland.beecode.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.SysParamDao;
import com.newland.beecode.domain.SysParam;
import com.newland.beecode.service.SysParamService;
/**
 * @author shaoxr:
 * @version 2011-7-16 上午11:20:38
 * 
 */
@Service(value="sysParamService")
public class SysParamServiceImpl implements SysParamService{
	@Autowired
	private SysParamDao sysParamDao;

	@Override
	public void update(SysParam sp) {
		SysParam _sp=this.findById(sp.getId());
		_sp.setParamValue(sp.getParamValue());
		this.sysParamDao.update(_sp);
		
	}

	@Override
	public List<SysParam> findByEntries(Integer start, Integer size) {
		return this.sysParamDao.findListByQuery("from SysParam s ", start, size);
	}

	@Override
	public long countSysParam() {
		return this.sysParamDao.findLong("select count(*) from SysParam s");
	}

	@Override
	public SysParam findById(Long id) {
		return this.sysParamDao.get(id);
	}

	@Override
	public SysParam findBykey(String key) {
		return this.sysParamDao.findUniqueByProperty("paramKey", key);
	}

}
