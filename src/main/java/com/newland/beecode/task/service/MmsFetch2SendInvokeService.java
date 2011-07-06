package com.newland.beecode.task.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.RespStatusDao;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.service.MMSService;
import com.newland.beecode.service.SendListService;
import com.newland.beecode.task.SendParam;

/**
 * @author shaoxr:
 * @version 2011-5-31 下午02:31:34
 * 
 */
@Service(value = "mmsFetch2SendInvokeService")
public class MmsFetch2SendInvokeService implements SendInvokeService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private MMSService mmsService;
	@Autowired
	private RespStatusDao respStatusDao;
	@Autowired
	private SendListService sendListService;

	private Integer msType = SendList.MS_TYPE_MMS;

	@Override
	public void sendRun(SendParam sp) throws Exception {
		// RespStatus respstatus = new RespStatus();
		// respstatus.setRespDesc("success");
		// String[] str=mmsService.sendMMS(sp);
		// logger.debug("-------send :"+str[0]);
		// if(str[0].indexOf("OK")>=0){
		// respstatus.setRespStatus(RespStatus.RESP_SUCCESS);
		// }else{
		// respstatus.setRespStatus(RespStatus.RESP_ERROR);
		// respstatus.setRespDesc(str[0]);
		// }
		// logger.debug("-------send :"+sp.getMobile());
		// respstatus.setCouponId(sp.getCouponId().toString());
		// respstatus.setMmsSendListId(sp.getSendListId());
		// this.respStatusDao.save(respstatus);
		RespStatus respstatus = new RespStatus();
		try {
			String resp = this.mmsService.sendMMSByMontnets(sp);
			if (resp.length() > 10) {
				logger.debug("send success mobile:" + sp.getMobile());
				respstatus.setRespStatus(RespStatus.RESP_SUCCESS);
				respstatus.setRespDesc(resp);
			} else {
				respstatus.setRespStatus(RespStatus.RESP_ERROR);
				respstatus.setRespDesc(resp);
				logger.debug("send error! error code:"+resp+";mobile:" + sp.getMobile());
			}
		} catch (Exception e) {
			respstatus.setRespStatus(RespStatus.RESP_ERROR);
			respstatus.setRespDesc("connect error");
			logger.error("", e);
		}
		
		respstatus.setCouponId(sp.getCouponId().toString());
		respstatus.setMmsSendListId(sp.getSendListId());
		this.respStatusDao.save(respstatus);
	}

	@Override
	public void sendOver(Long actNo, Long sendListId) {
		this.sendListService.sendOver(sendListId);

	}

	@Override
	public Integer getMsType() {
		return this.msType;
	}

}
