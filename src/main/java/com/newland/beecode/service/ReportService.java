package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;

public interface ReportService {
	
	public ReportResult queryReport(ReportForm reportForm);

}
