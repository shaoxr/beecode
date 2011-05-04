package com.newland.beecode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.beecode.dao.RoleDao;
import com.newland.beecode.domain.Roles;
import com.newland.beecode.service.RolesService;

@Service(value = "RolesService")
public class RolesServiceImpl implements RolesService {

	@Resource(name = "roleDao")
    private RoleDao roleDao;
	
	@Override
	public List<Roles> findRolesEntries(int firstResult, int maxResults) {
		return this.roleDao.findRolesEntries(firstResult, maxResults);
	}

	@Override
	public long countRoleses() {
		return this.roleDao.countRoleses();
	}

	@Override
	public void save(Roles roles) {
		this.roleDao.save(roles);
	}

	@Override
	public Roles findUniqueByProperty(String propertyName,Object value) {
		return this.roleDao.findUniqueByProperty(propertyName, value);
	}

	@Override
	public List<Roles> findAll() {
		return this.roleDao.findAll();
	}

	@Override
	public void update(Roles roles) {
		this.roleDao.update(roles);
	}

	@Override
	public void delete(Long id) {
		this.roleDao.delete(id);
	}

}
