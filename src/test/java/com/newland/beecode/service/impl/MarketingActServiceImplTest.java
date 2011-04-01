package com.newland.beecode.service.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.report.MarketingActSummary;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
public class MarketingActServiceImplTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testSummary(){
		MarketingActSummary mas = MarketingAct.marketingSummary(1l);
		System.err.println(mas.getUsedTimes() + "/" + mas.getRemainTimes() + "/" + mas.getJoinCount());
		
	}

}
