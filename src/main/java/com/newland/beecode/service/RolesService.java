package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Roles;

public interface RolesService {

	public List<Roles> findRolesEntries(int firstResult, int maxResults);
	
	public long countRoleses();
	
	public void save(Roles roles);
	
	public Roles findUniqueByProperty(String propertyName,Object value);
	
	public List<Roles> findAll();
	
	public void update(Roles roles);
	
	public void delete(Long id);
}
