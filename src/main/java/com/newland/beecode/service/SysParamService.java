package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.SysParam;

/**
 * @author shaoxr:
 * @version 2011-7-16 上午11:20:22
 * 
 */
public interface SysParamService {
	
	public void update(SysParam sp);
	
	public List<SysParam> findByEntries(Integer start,Integer size);
	
	public long countSysParam();
	
	public SysParam findById(Long id);
	
	public SysParam findBykey(String key);

}
