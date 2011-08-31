package com.newland.beecode.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.dao.RespStatusDao;
import com.newland.beecode.dao.sendListDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MsStatus;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.domain.SmsSendList;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.domain.SysParam;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.FileService;
import com.newland.beecode.service.SendListService;
import com.newland.beecode.service.SendService;
import com.newland.beecode.service.SysParamService;
import com.newland.beecode.task.service.SendInvokeService;

/**
 * @author shaoxr:
 * @version 2011-5-16 下午05:53:19
 * 
 */
@Service(value="sendListService")
public class SendListServiceImpl implements SendListService{
	protected Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private SendService sendService;
	@Autowired
	private FileService fileService;
	@Autowired
    private sendListDao sendListDao;
	@Autowired
	private RespStatusDao respStatusDao;
	@Resource(name="mmsFetch2SendInvokeService")
	private SendInvokeService mmsFetch2SendInvokeService;
	@Resource(name="smsFetch2SendInvokeService")
	private SendInvokeService smsFetch2SendInvokeService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private SysParamService sysParamService;
	
	@Override
	public SendList saveOrUpdate(SendList mmsSendList) {
		return sendListDao.saveOrUpdate(mmsSendList);
		
	}
	@Override
	public List<SendList> findByLimit(Integer start, Integer end) {
		return this.sendListDao.findListByQuery("from SendList m order by m.submitTime desc ", start, end);
	}
	@Override
	public long countAll() {
		return this.sendListDao.findLong("select count(*) from SendList");
	}
	@Override
	public SendList findById(Long id) {
		return this.sendListDao.get(id);
	}
	@Override
	public void sendOver(Long id) {
		long successCount=this.respStatusDao.findLong("select count(*) from RespStatus r where r.respStatus=? and r.mmsSendListId=?", RespStatus.RESP_SUCCESS,id);
		sendListDao.excuteByHQL("update SendList m set  m.sendStatus=?,m.successCount=?,m.endTime=? where m.id=?",SmsSendList.STATUS_SENDED,successCount,new Date(),id);
		
	}
	@Override
	@Transactional
	public MsStatus findSendCount() {
		MsStatus msStatus=new MsStatus();
		String sql ="select count(t.mmssuccess),count(t.smssuccess),count(*) from " + 
           "(select case c.mstype when "+SendList.MS_TYPE_MMS+" then c.mstype else null end as mmssuccess, case c.mstype when "+SendList.MS_TYPE_SMS+" then c.mstype else null end as smssuccess from " +
           "(select s.ms_type as mstype from resp_status r inner JOIN send_list s on r.mms_send_list_id=s.id where r.resp_status = "+RespStatus.RESP_SUCCESS+" ) as c  ) as t";
		List<Object[]> list = this.sendListDao.findBySql(sql);
		if (!list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			msStatus.setMMSSuccessCount(((BigInteger)obj[0]).longValue());
			msStatus.setSMSSuccessCount(((BigInteger)obj[1]).longValue());
			msStatus.setTotalCount(((BigInteger)obj[2]).longValue());
			return msStatus;
		}
		return msStatus;
	}
	@Override
	public void send(final String  tempFileName,final Integer msType) throws AppException {
		new Thread(){
			public void run() {
				SendList mmsSendList=new SendList();
				try {
					mmsSendList.setSubmitTime(new Date());
					mmsSendList.setSendStatus(SendList.STATUS_SENDING);
					mmsSendList.setMsType(msType);
					mmsSendList=saveOrUpdate(mmsSendList);
					;
					String dirName=fileService.extractMms(tempFileName);
					List<Coupon> coupons=fileService.getCoupon(dirName);
					MarketingAct act=fileService.getActFile(dirName, msType);
					mmsSendList.setTotalCount(new Long(coupons.size()));
					mmsSendList.setSubmitTime(new Date());
					mmsSendList.setSendStatus(SendList.STATUS_SENDING);
					mmsSendList.setMsType(msType);
					mmsSendList.setActName(act.getActName());
					mmsSendList.setActNo(act.getActNo());
					mmsSendList=saveOrUpdate(mmsSendList);
					if(msType.equals(SendList.MS_TYPE_MMS)){
						if(sysParamService.findBykey(SysParam.SEND_MGR_NAME).getParamValue().equals(SysParam.SEND_MGR_DX_SMS)){
							/*if(coupons.size()==1){
								sendService.sendOne(coupons.get(0), act, mmsFetch2SendInvokeService, mmsSendList.getId(), dirName);
							}else{
								sendService.differenceSend(coupons, act, mmsSendList.getId(), dirName);
							}*/
							sendService.differenceSend(coupons, act, mmsSendList.getId(), dirName);
						}else{
							if(coupons.size()==1){
								sendService.sendOne(coupons.get(0), act, mmsFetch2SendInvokeService, mmsSendList.getId(), dirName);
							}else{
								sendService.send(coupons, act, mmsFetch2SendInvokeService, mmsSendList.getId(), dirName);
							}
						}
						
						
					}else{
						if(coupons.size()==1){
							sendService.sendOne(coupons.get(0), act, smsFetch2SendInvokeService, mmsSendList.getId(), dirName);
						}else{
							sendService.send(coupons, act, smsFetch2SendInvokeService, mmsSendList.getId(), dirName);
						}
						
					}
					
				} catch (Exception e) {
					logger.error("", e);
					String message="";
					if(e instanceof AppException){
						message=messageSource.getMessage(((AppException)e).getCode(), null, Locale.CHINA);
					}else{
						message=messageSource.getMessage(ErrorsCode.BIZ_MMS_SEND_ERROR_FILE, null, Locale.CHINA);
					}
					sendListDao.excuteByHQL("update SendList s set s.sendStatus=?,s.message=? where s.id=?",SmsSendList.STATUS_SENDED, message,mmsSendList.getId());
				}
			}
		}.start();
		
		
	}

	
	

}
