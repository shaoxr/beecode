package com.newland.beecode.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.OperDao;
import com.newland.beecode.dao.RoleDao;
import com.newland.beecode.domain.Oper;
import com.newland.beecode.domain.Roles;
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
	@Autowired
	private RoleDao roleDao;

	@Override
	public void save(Oper oper,Long[] roles) {
		oper.setRoles(this.genRoles(roles));
		this.operDao.save(oper);		
	}
	
	private Set<Roles>  genRoles(Long[] rolesIds){
		Set<Roles> roles=new HashSet<Roles>();
		for(Long rolesId:rolesIds){
			Roles role=this.roleDao.get(rolesId);
			roles.add(role);
		}		
		return roles;	
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
	public void update(Oper oper,Long[] roles) {
		oper.setRoles(this.genRoles(roles));
		
		System.out.println(oper.getOperNo());
		System.out.println(oper.getOperName());
		System.out.println(oper.isEnabled());
		System.out.println(oper.getRoles().size());
		this.operDao.saveOrUpdate(oper);
		System.out.println(oper.getVersion());
	}

	@Override
	public void delete(Long id) {
		this.operDao.delete(id);	
	}

	@Override
	public List<Roles> findRoleAll() {
		return this.roleDao.findAll();
	}


}
