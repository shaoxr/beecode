package com.newland.beecode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.beecode.dao.OperDao;
import com.newland.beecode.domain.Oper;
import com.newland.beecode.service.OperService;

/**
 * @author shaoxr:
 * @version 2011-4-26 下午06:49:24
 * 
 */
@Service(value="operService")
public class OperServiceImpl implements OperService{
	
	@Resource(name="operDao")
	private OperDao operDao;

	@Override
	public void save(Oper oper) {
		this.operDao.save(oper);
		
	}

	@Override
	public List<Oper> findAll() {
		return this.operDao.findAll();
	}

	@Override
	public Oper findById(Long id) {
		return this.operDao.get(id);
	}

	@Override
	public List<Oper> findOperEntries(Integer start, Integer end) {
		return this.operDao.findOperEntries(start, end);
	}

	@Override
	public long countOpers() {
		return this.operDao.countOpers();
	}

	@Override
	public void update(Oper oper) {
		this.operDao.update(oper);
		
	}

	@Override
	public void delete(Long id) {
		this.operDao.delete(id);
		
	}
	
	

}
