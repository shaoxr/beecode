package com.newland.beecode.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.AppRTException;
import com.newland.beecode.service.CheckService;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.TransactionalService;

/**
 * @author shaoxr:
 * @version 2011-5-13 下午03:44:40
 * 
 */
@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-*.xml"}) 
public class TransactionTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private TransactionalService transactionalService;
	@Autowired
	private MarketingActService marketingActService;
	@Autowired
	private FileService fileService;
	@Autowired
	private CheckService checkService;
	@Autowired
	private CouponService couponService;
	@Test
	public void testFuck(){
		//this.transactionalService.firstTransaction(new Long(38));
		MarketingAct act=this.marketingActService.findByActNo(new Long(38));
	}

}
