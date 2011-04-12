package com.newland.beecode.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.CoupontCtrl;
import com.newland.utils.NewlandUtil;

public class CouponCtrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
