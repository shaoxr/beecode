package com.newland.beecode.dao;

import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import com.newland.beecode.dao.OperDao;
import com.newland.beecode.domain.Oper;


@ContextConfiguration(locations = "/applicationContext.xml") 
public class OperDaoTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="operDao")
	private OperDao operDao;

	@Test 
	public void testFindAll(){
		List<Oper> oper = operDao.findAll();
		for(Oper op : oper){
			System.out.println(op.getOperName());
		}
	}
	@Test  
	public void testFindOperEntries(){
		List<Oper> oper = operDao.findOperEntries(0,10);
		System.out.println(oper.size());
		for(Oper op : oper){
			System.out.println(op.getOperName());
		}
	}
	@Test  
	public void testCountOpers(){
		System.out.println(operDao.countOpers());
		
	}
}
