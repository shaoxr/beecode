package com.newland.beecode.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.beecode.dao.sendListDao;
import com.newland.beecode.domain.MsStatus;
import com.newland.beecode.domain.RespStatus;
import com.newland.beecode.domain.SmsSendList;
import com.newland.beecode.domain.SendList;
import com.newland.beecode.service.SendListService;

/**
 * @author shaoxr:
 * @version 2011-5-16 下午05:53:19
 * 
 */
@Service(value="sendListService")
public class SendListServiceImpl implements SendListService{
	@Autowired
    private sendListDao sendListDao;
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
		sendListDao.excuteByHQL("update SendList m set m.sendStatus=?,m.endTime=? where m.id=?",SmsSendList.STATUS_SENDED,new Date(),id);
		
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
	
	

}
