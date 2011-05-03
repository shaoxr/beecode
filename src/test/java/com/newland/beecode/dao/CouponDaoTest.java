package com.newland.beecode.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.condition.CouponCondition;



@ContextConfiguration(locations = "/applicationContext.xml") 
public class CouponDaoTest extends AbstractJUnit4SpringContextTests{
	@Resource(name = "couponDao")
    private CouponDao couponDao;
	
	@Test
	public void testFindByCondition(){
		CouponCondition cc=new CouponCondition();
		cc.setMobile("18605089033");
		
		List<Coupon> coupon = couponDao.excuteSimpleQuery(cc,0,10);
		for(Coupon cp : coupon){
			System.out.println(cp.getCouponId());
		}
		System.out.println("------------------>");
		}

}
