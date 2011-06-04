package com.newland.beecode.service;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.exception.AppRTException;

/**
 * 二维码生成服务
 * @author shaoxr
 *
 */
public interface BarCodeService {
	/**
	 * 单个二维码生成
	 * @param Str
	 */
	public void genCode(String str,String fileName)throws AppRTException;
	/**
	 * 批量二维码生成
	 * @param Str
	 */
	public void genCode(String [] Str);
	/**
	 * 单个二维码生成
	 * @param coupon
	 */
	public void genCode(Coupon coupon);

}
