package com.newland.beecode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.dao.CustomerDao;
import com.newland.beecode.dao.MarketingActDao;
import com.newland.beecode.domain.Customer;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.AppRTException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.TransactionalService;

/**
 * @author shaoxr:
 * @version 2011-5-13 下午03:10:06
 * 
 */
@Service(value="transactionalService")
public class TransactionalServiceImpl implements TransactionalService{
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
    private MarketingActService marketingActService;
	@Resource(name = "marketingActDao")
	private MarketingActDao actDao;
	@Autowired
	private CustomerDao customerDao;
	
	
    @Override
    @Transactional
	public void append(Long actNo, MultipartFile file, String bizNo) throws AppException {
		this.marketingActService.append(actNo, file,bizNo);
		
	}
	@Override
	@Transactional
	public long[] checkMarketingAct(Long actNo, Integer actStatus)throws AppException {
		long success=0;
		long fail=0;
		MarketingAct act;
		synchronized(this){
			act = actDao.findUniqueByProperty("actNo", actNo);
            if(!act.getActStatus().equals(MarketingAct.STATUS_BEFORE_RECHECK)&&!act.getActStatus().equals(MarketingAct.STATUS_APPEND_CUSTOMER)){
				throw new AppException(ErrorsCode.BIZ_COUPON_CHECKED,"");
			}
            Integer keepStatus=act.getActStatus();
			act.setActStatus(actStatus);
			if (actStatus .equals( MarketingAct.STATUS_BEFORE_GIVE)) {
				act.setActStatus(MarketingAct.STATUS_BEFORE_GIVE);
				List<Customer> customers=this.customerDao.find("from Customer c where c.actNo=? and fileStatus=?", act.getActNo(),Customer.CUSTOMER_STATUS_UNCHECK);
				for(Customer customer:customers){
					try{
					  this.marketingActService.checkMarketingActByLimit(act, customer);
					  customer.setFileStatus(Customer.CUSTOMER_STATUS_CHECKED);
					  this.customerDao.update(customer);
					  success=success+customer.getCount();
					}catch(Exception e){
						if(e instanceof AppRTException){
							logger.error(((AppRTException)e).getCode(), e);
						}
						logger.error("", e);
						act.setActStatus(keepStatus);
						fail=fail+customer.getCount();
					}
				}
			}
			actDao.update(act);
		}
		return new long[]{success,fail};
	}

}
