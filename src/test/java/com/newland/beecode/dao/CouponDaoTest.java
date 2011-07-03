package com.newland.beecode.dao;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.CouponCtrl;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.service.CheckService;



@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-*.xml"}) 
public class CouponDaoTest extends AbstractJUnit4SpringContextTests{
	@Resource(name = "couponDao")
    private CouponDao couponDao;
	
	@Resource(name = "couponCtlDao")
    private CouponCtrlDao ctrlDao;
	@Autowired
	private CheckService checkService;
	
	@Test
	public void testFindByCondition(){
		File file =new File("e:/names.xls");
		File file12=new File("e:/names12.xls");
		List<Customer> list=this.checkService.newland(file);
		System.out.println(list.size());
		for(Customer c:list){
			Coupon coupon=new Coupon();
			coupon.setAcctMobile(c.getMobile());
			coupon.setMmsStatus(0);
            this.couponDao.save(coupon);
		}
		Coupon coupon=new Coupon();
		coupon.setAcctMobile("18606060518");
		coupon.setMmsStatus(0);
        this.couponDao.save(coupon); 
		
            

		}
	
	

}
