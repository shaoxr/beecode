package com.newland.beecode.dao;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import com.newland.beecode.dao.PartnerDao;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
 
@ContextConfiguration(locations = "/applicationContext.xml") 

public class PartnerDaoTest extends AbstractJUnit4SpringContextTests{
	
	@Resource(name="partnerDao")
	private PartnerDao partnerDao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	@Test
	public void testDelete(){
		Partner partner=this.partnerDao.get(new Long(4));
		partner.setId(new Long(12));
		partner.setPartnerName("ssssssa");
		partner.setPartnerNo("1111111");
		partner.setVersion(0);
		//partner.setPartnerCatalog(partnerCatalog);
		partnerDao.save(partner);
		
	}
	@Test
	public void testUpdate(){
		Partner partner=this.partnerDao.get(new Long(4));
		partner.setPartnerName("ttttt");
		partner.setPartnerNo("19999");
		partnerDao.update(partner);
	}
}
