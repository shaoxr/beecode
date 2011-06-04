package com.newlandcomputer.posp.txn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newland.beecode.service.BeecodeService;
import com.newland.beecode.service.impl.CouponBackoffRequest;
import com.newland.beecode.service.impl.CouponCheckRequest;
import com.newland.beecode.service.impl.RespParam;
import com.newland.utils.NewlandUtil;


public class InvokePosServiceImpl implements InvokePosSerivice {
	private final Log logger = LogFactory.getLog(InvokePosServiceImpl.class);
    public void setBeecodeService(BeecodeService beecodeService) {
		this.beecodeService = beecodeService;
	}
	private BeecodeService beecodeService;
	@Override
	public Map txn(Map reqMap,String transType) {
		if("97".equals(transType)){
			return this.beecodeService.voucherBackoff(reqMap);
		}
		if("98".equals(transType)){
			return this.beecodeService.discountBackout(reqMap);
			
		}if("99".equals(transType)){
			return this.beecodeService.exchangeBackoff(reqMap);
		}if("00".equals(transType)){
			return this.beecodeService.exchange(reqMap);
		}
		if("01".equals(transType)){
			return this.beecodeService.discount(reqMap);	
		}
		if("02".equals(transType)){
			return this.beecodeService.voucher(reqMap);
		}
		if("10".equals(transType)){
			return this.beecodeService.query(reqMap);
		}
		return new HashMap();
	}
	

}
