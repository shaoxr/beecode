package com.newland.beecode.service;

import java.util.List;


import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;

/**
 * @author shaoxr:
 * @version 2011-5-12 下午08:05:54
 * 
 */
public interface CustomerService {
	
	
	public List<Customer> findByActNo(Long actNo);
	
	
	public void genCustomerList(MarketingAct act);
	
	public Customer saveOrUpdate(Customer customer);
		
	public void delete(Long id);
	
	public Customer findByNewName(String newName);
}
