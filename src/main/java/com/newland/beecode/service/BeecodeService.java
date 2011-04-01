package com.newland.beecode.service;


import java.util.Map;

import com.newland.beecode.service.impl.CouponBackoffRequest;
import com.newland.beecode.service.impl.CouponCheckRequest;
import com.newland.beecode.service.impl.CouponQueryRequest;
import com.newland.beecode.service.impl.RespParam;

public interface BeecodeService {
	// 兑换券冲正
	public Map exchangeBackoff(Map reqMap);

	// 兑换券兑换
	public Map exchange(Map reqMap);

	// 折扣验证
	public Map discountCheck(Map reqMap);

	// 折扣登记
	public Map discount(Map reqMap);

	// 折扣冲正
	public Map discountBackout(Map reqMap);

	// 折扣券查询
	public Map discountQuery(Map reqMap);
}
