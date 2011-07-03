package com.newland.beecode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.StoreDao;
import com.newland.beecode.domain.Store;
import com.newland.beecode.service.StoreService;

/**
 * @author shaoxr:
 * @version 2011-6-24 下午01:19:25
 * 
 */
@Service(value="storeService")
public class StoreServiceImpl implements StoreService{
	@Autowired
	private StoreDao storeDao;

	@Override
	public void save(Store store) {
		this.storeDao.save(store);
		
	}

	@Override
	public List<Store> findStoreEntries(Integer start, Integer max) {
		return this.storeDao.findListByQuery("from Store s", start, max);
	}

	@Override
	public long countStore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Store findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Store store) {
		// TODO Auto-generated method stub
		
	}

}
