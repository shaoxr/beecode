package com.newland.utils;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;

/**
 * @author shaoxr:
 * @version 2011-5-25 下午10:04:27
 * 
 */
public class BarCodeFormat {
	
	public static String genBarCodeCode(Coupon coupon,MarketingAct act){
		StringBuffer sb=new StringBuffer();
		sb.append(coupon.getBusinessType());
		if(act.getBizNo().equals(Coupon.BIZ_TYPE_VOUCHER)){
			sb.append(NewlandUtil.formatBigDecimal(coupon.getBackAmount(), 10));
		}else if(act.getBizNo().equals(Coupon.BIZ_TYPE_DISCOUNT)){
			sb.append(NewlandUtil.formatBigDecimal(coupon.getRebateRate(), 2));
			sb.append(NewlandUtil.formatBigDecimal(act.getMaxAmount(), 8));
		}else{
			sb.append(NewlandUtil.formatBigDecimal(coupon.getRebateRate(), 10));
		}
		sb.append(NewlandUtil.dataToString(act.getEndDate(), "yyyyMMddHHmm"));
		sb.append(coupon.getSerialNo());
		return sb.toString();
		
	}

}
