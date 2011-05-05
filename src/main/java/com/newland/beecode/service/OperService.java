package com.newland.beecode.service;

import java.util.List;


import com.newland.beecode.domain.Oper;
import com.newland.beecode.domain.Roles;

/**
 * @author shaoxr:
 * @version 2011-4-26 下午06:49:06
 * 
 */
public interface OperService {
	
	public void save(Oper oper,Long[] roles);
	
	public List<Oper> findAll();
	
	public List<Roles> findRoleAll();
	
	public Oper findById(Long id);
	
	public List<Oper> findOperEntries(Integer start,Integer end);
	
	public long countOpers();
	
	public void update(Oper oper,Long[] roles);
	
	public void delete(Long id);
	
	public Oper findOperByOperName(String operName);

}
