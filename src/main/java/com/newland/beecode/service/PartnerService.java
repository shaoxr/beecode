package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.report.PartnerSummaryReport;
import com.newland.beecode.exception.AppException;

public interface PartnerService {

	public PartnerSummaryReport summaryReport(Long id);
	
	public List<Partner> findByCatalog(Long catalogId);
	
	public List<Partner> findAll();
	
	public void save(Partner partner) throws AppException;
	
	public Partner findById(Long id);
	
	public List<Partner> findPartnerEntries(Integer start,Integer end);
	
	public long countPartners();
	
	public void delete(Long id);
}
