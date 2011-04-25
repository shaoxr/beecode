package com.newlandcomputer.posp.txn;

import java.math.BigDecimal;
import java.util.Date;
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
	public Map discount( Map reqMap) {
		logger.debug("discount rec Map： "+reqMap);
		Map respMap=this.beecodeService.discount(reqMap);
		logger.debug("discount return Map： "+respMap);
		return respMap;
	}
	public Map discountBackout( Map reqMap) {
		logger.debug("discountBackout rec Map： "+reqMap);
		Map respMap=this.beecodeService.discountBackout(reqMap);
		logger.debug("discountBackout return Map： "+respMap);
		return respMap;
	}
	public Map discountCheck(Map reqMap) {
		logger.debug("discountCheck  rec  Map： "+reqMap);
		Map respMap=this.beecodeService.discountCheck(reqMap);
		logger.debug("discountCheck return Map： "+respMap);
		return respMap;
	}
	public Map exchange( Map reqMap) {
		logger.debug("exchange rec Map： "+reqMap);
		Map respMap=this.beecodeService.exchange(reqMap);
		logger.debug("exchange return Map： "+respMap);
		return respMap;
	}
	public Map exchangeBackout( Map reqMap) {
		logger.debug("exchangeBackout rec Map： "+reqMap);
		Map respMap=this.beecodeService.exchangeBackoff(reqMap);
		logger.debug("exchangeBackout return  Map： "+respMap);
		return respMap;
	}
	public Map discountQuery( Map reqMap) {
		logger.debug("discountQuery rec Map： "+reqMap);
		Map respMap=this.beecodeService.discountQuery(reqMap);
		logger.debug("discountQuery return Map： "+respMap);
		return respMap;
	}
	

}
