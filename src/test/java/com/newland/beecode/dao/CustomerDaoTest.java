package com.newland.beecode.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests; 

import com.newland.beecode.domain.Customer;

@ContextConfiguration(locations = "/applicationContext.xml") 

public class CustomerDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Resource(name = "customerDao")
	private CustomerDao customerDao;
	
	@Test
	public void testExcuteByHQL(){
		Customer customer = (Customer)this.customerDao.findUnique("from Customer c where c.newName=?", "481305702938874");
		//this.customerDao.find("from Customer c where c.newName=?", "481305702938874");
		System.out.println("1111111111");
		//this.customerDao.excuteByHQL("delete from customer c where c.newName=?", "481305702938874");		
		System.out.println(customer.getId());
		System.out.println("1111111111");
	}

}
