package com.newland.beecode.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.CoupontCtrl;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.service.CouponService;
import com.newland.utils.NewlandUtil;
import com.newland.utils.UuidHelper;

//@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
public class CouponServiceImplTest {

	CouponService couponService;
    
	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void testConsumeCoupon() throws AppException {
		//consume(6010061761l, "269c0583-3b26-11e0-8fae-65f97f25070c", "000-001");
		//consume(6004090321l, "24b7dc2d-3b26-11e0-8fae-c37c56a2153e", "000-003");
		//consume(6005042161l, "24b8033e-3b26-11e0-8fae-df5802e5fca4", "000-002");
		//consume(3008077142l, "bf53e211-3a38-11e0-9e27-2301bc7fe628", "000-002");
		//consume(6011038074l, "269c53a4-3b26-11e0-8fae-11c58f3d853e", "000-001");
		//backoff();
		//report();
		fuck();
	}

	public void consume(Long couponId, String serialNO, String partnerNo)
			throws AppException {
		Coupon coupon = Coupon.findCoupon(couponId);
		CouponCheckRequest req = new CouponCheckRequest();
		req.setCouponId(couponId);
		req.setDeviceNo("000000");
		req.setSerialNo(serialNO);
		req.setPartnerNo(partnerNo);
		req.setBizType(coupon.getBusinessType());
		

		//couponService.consumeCoupon(req);
	}
	public void backoff(){
		CouponBackoffRequest req=new CouponBackoffRequest();
		req.setSerialNo("89c295b3-3ef7-11e0-bfcc-");
		this.couponService.backOff(req);
	}
	public void report(){
		ReportForm rf=new ReportForm();
		rf.setPagination(false);
		Coupon.reportDetail(rf);
	}
	public void genCtrl(){
		for(int i=0;i<=200;i++){
			Coupon coupon=new Coupon();
			coupon.setAcctMobile("18650071122");
			coupon.setAcctNo("1618");
			coupon.setBusinessType("01");
			coupon.setCheckCode("0011");
			coupon.setCouponStatus(4);
			coupon.setGenTime(new Date());
			coupon.setRebateRate(5.0f);
			coupon.setRemainTimes(0);
			coupon.setSerialNo(UuidHelper.generateUUID());
			coupon.setTimes(1);
			coupon.setVersion(0);
			//coupon.persist();
			
			CoupontCtrl ct=new CoupontCtrl();
			ct.setBatchNo("000001");
			
			ct.setBusinessType("00");
			ct.setCheckDate(new Date());
			if(i<49){
				
			   ct.setCheckDay(NewlandUtil.string2Date("2011-03-05", "yyyy-MM-dd"));
			   ct.setPartnerNo("104110058120005");
			}
			if(i>=49&& i<138){
				ct.setCheckDay(NewlandUtil.string2Date("2011-03-04", "yyyy-MM-dd"));
				ct.setPartnerNo("104110058120006");
			}
			if(i>=138){
				ct.setCheckDay(NewlandUtil.string2Date("2011-03-05", "yyyy-MM-dd"));
				ct.setPartnerNo("104110058120007");
			}
			ct.setCouponId(coupon.getCouponId());
			ct.setDeviceNo("11000007");
			ct.setEncodeVersion("2");
			ct.setRebateRate(new BigDecimal(5.0));
			ct.setSerialNo(coupon.getSerialNo());
			ct.setTraceNo(String.valueOf(100000+i));
			ct.setVersion(0);
			ct.persist();
		}
		
		
	}
	public void fuck(){
		List<Coupon> list=Coupon.findAllCoupons();
		int i=0;
		for(Coupon c:list){
				CoupontCtrl ct=new CoupontCtrl();
				ct.setBatchNo("000001");
				
				ct.setBusinessType("00");
				ct.setCheckDate(new Date());
				if(Math.random()<0.5){
					
				   ct.setCheckDay(NewlandUtil.string2Date("2011-03-05", "yyyy-MM-dd"));
				}
				else{
					ct.setCheckDay(NewlandUtil.string2Date("2011-03-04", "yyyy-MM-dd"));
					
				}
				ct.setPartnerNo("104110058120006");
				ct.setCouponId(c.getCouponId());
				ct.setDeviceNo("11000007");
				ct.setEncodeVersion("2");
				ct.setAmount(new BigDecimal(1000));
				ct.setSerialNo(c.getSerialNo());
				ct.setTraceNo(String.valueOf(100000+(i++)));
				ct.setVersion(0);
				ct.persist();
			
		}
	}

}
