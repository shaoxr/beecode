package com.newland.beecode.domain;

import java.util.Date;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.transaction.annotation.Transactional;

import com.newland.utils.UuidHelper;

@RooIntegrationTest(entity = Coupon.class)
public class CouponIntegrationTest {

	@Test
	@Transactional
	public void testMarkerMethod() {
		for (int i = 0; i < 2; i++) {

			Coupon coupon = new Coupon();
			coupon.setAcctNo("adfsafd");
			coupon.setAcctMobile("asdfsf");

			coupon.setMarketingAct(null);
			coupon.setCouponStatus(Coupon.STATUS_VALID);
			coupon.setSerialNo(UuidHelper.generateUUID());
			coupon.setBusinessType("asdfsdf");
			coupon.setRebateRate(Float.valueOf("1"));
			coupon.setTimes(1);
			coupon.setRemainTimes(1);
			coupon.setGenTime(new Date());
			coupon.persist();
			System.err.println(coupon.getCouponId());
		}
	}
}
