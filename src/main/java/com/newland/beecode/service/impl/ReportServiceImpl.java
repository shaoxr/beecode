package com.newland.beecode.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.service.ReportService;
@Service("reportService")
public class ReportServiceImpl implements ReportService{

	@Override
	public ReportResult queryReport(ReportForm reportForm) {
		return Coupon.reportDetail(reportForm);
	}

}
