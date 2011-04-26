package com.newland.beecode.service.impl;


import org.springframework.stereotype.Service;

import com.newland.beecode.dao.CouponDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.service.ReportService;
import javax.annotation.Resource;
@Service("reportService")
public class ReportServiceImpl implements ReportService{

    @Resource(name = "couponDao")
    private CouponDao couponDao;
    
	@Override
	public ReportResult queryReport(ReportForm reportForm) {
		//return Coupon.reportDetail(reportForm);
            return couponDao.reportDetail(reportForm);
	}

}
