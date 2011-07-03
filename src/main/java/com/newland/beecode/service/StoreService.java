package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Store;

/**
 * @author shaoxr:
 * @version 2011-6-24 下午01:19:02
 * 
 */
public interface StoreService {
	
	public void save(Store store);
	
	public List<Store> findStoreEntries(Integer start,Integer max);
	
	public long countStore();
	
	public void delete(Long id);
	
	public Store findById(Long id);
	
	public void update(Store store);

}
