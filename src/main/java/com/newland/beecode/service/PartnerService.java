package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.report.PartnerSummaryReport;

public interface PartnerService {

	public PartnerSummaryReport summaryReport(Long id);
	
	public List<Partner> findByCatalog(Long catalogId);
	
	public List<Partner> findAll();
}
