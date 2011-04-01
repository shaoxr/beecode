package com.newland.beecode.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.newland.beecode.domain.report.PartnerSummaryReport;
import com.newland.beecode.service.PartnerService;

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
public class PartnerServiceImplTest {

	@Autowired
	private PartnerService partnerService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSummaryReport() {
		PartnerSummaryReport rpt = partnerService.summaryReport(1l);
		System.out.println(rpt.getDayRpts().size() + "/" + rpt.getItems().size() + "/" + rpt.getMarketingRpts().size());
	}

}
