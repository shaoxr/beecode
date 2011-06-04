package com.newland.beecode.service;

import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;

public interface ReportService {
	
	public ReportResult queryReport(ReportForm reportForm);

}
