package com.newland.beecode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.beecode.dao.CustomerDao;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.service.CustomerService;

/**
 * @author shaoxr:
 * @version 2011-5-12 下午08:06:07
 * 
 */
@Service(value="customerService")
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDao customerDao;

	@Override
	public List<Customer> findByActNo(Long actNo) {
		return this.customerDao.findByProperty("actNo", actNo);
	}


	@Override
	public Customer saveOrUpdate(Customer customer) {
		return this.customerDao.saveOrUpdate(customer);
		
	}

	@Override
	public void delete(Long id) {
		this.customerDao.delete(id);
		
	}

	@Override
	public Customer findByNewName(String newName) {
		Customer customer = (Customer)this.customerDao.findUnique("from Customer c where c.newName=?", newName);
		return customer;
	}

	@Override
	@Transactional
	public void genCustomerList(MarketingAct act) {
		List<Customer> unChcek= this.customerDao.find("from Customer c where c.actNo=? and fileStatus=?", act.getActNo(),Customer.CUSTOMER_STATUS_UNCHECK);
		
		List<Customer> checked=this.customerDao.find("from Customer c where c.actNo=? and fileStatus=?", act.getActNo(),Customer.CUSTOMER_STATUS_CHECKED);
		act.setUnCheckCustomers(unChcek);
		act.setCheckedCustomers(checked);
	}


}
