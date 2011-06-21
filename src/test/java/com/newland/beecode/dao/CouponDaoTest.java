package com.newland.beecode.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.CouponCtrl;
import com.newland.beecode.domain.condition.CouponCondition;



@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-*.xml"}) 
public class CouponDaoTest extends AbstractJUnit4SpringContextTests{
	@Resource(name = "couponDao")
    private CouponDao couponDao;
	
	@Resource(name = "couponCtlDao")
    private CouponCtrlDao ctrlDao;
	
	@Test
	public void testFindByCondition(){
		List<CouponCtrl> list = ctrlDao.findByProperty("serialNo","3d4a56c8-a1a9-4690-8130-cf7635066a75");
		System.out.println(list.size());
		for (CouponCtrl coupontCtrl : list) {
        	System.out.println(coupontCtrl.getBatchNo()+":");
        	System.out.println(coupontCtrl.getDeviceNo()+":");
        	System.out.println(coupontCtrl.getPartnerNo()+":");
        	System.out.println(coupontCtrl.getTraceNo()+":");
        	System.out.println(coupontCtrl.getVoidFlag().equals(CouponCtrl.VOID_FLAG_NORMAL));
        }
		CouponCtrl c =ctrlDao.findUniqueByProperty("traceNo", "000107");
		System.out.println(c.getSerialNo());
		List<CouponCtrl> ctrls = ctrlDao.find("from CouponCtrl o where o.serialNo=? and o.batchNo=? and o.deviceNo=? and o.partnerNo=? and o.traceNo=? and o.voidFlag=? ", 
	    		"","","","","",CouponCtrl.VOID_FLAG_NORMAL);
		}
	
	

}
