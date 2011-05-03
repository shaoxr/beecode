package com.newland.beecode.dao;

import javax.annotation.Resource;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import com.newland.beecode.dao.PartnerCatalogDao;
 
@ContextConfiguration(locations = "/applicationContext.xml") 
public class PartnerCatalogDaoTest extends AbstractJUnit4SpringContextTests {
	@Resource(name="partnerCatalogDao")
    private PartnerCatalogDao partnerCatalogDao;
	@Test
	public void testFindPartnerCatalogsByCatalogName(){
		System.out.println("1111111111111");
	}
	}

