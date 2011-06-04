package com.newland.beecode.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests; 

import java.util.List;

import com.newland.beecode.domain.RespStatus;

@ContextConfiguration(locations = "/applicationContext.xml") 

public class RespStatusDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Resource(name = "respStatusDao")
	private RespStatusDao respStatusDao;
	
	@Test
	public void testFindByProperty(){
		List<RespStatus> respStatus = this.respStatusDao.findByProperty("mmsSendListId", new Long (17));
		for(RespStatus resp : respStatus){
			System.out.println("--------------------->");
			System.out.println(resp.getCouponId());
			
		}
	}

}
