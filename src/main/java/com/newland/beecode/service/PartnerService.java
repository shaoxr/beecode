package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.report.PartnerSummaryReport;

public interface PartnerService {

	public PartnerSummaryReport summaryReport(Long id);
	
	public List<Partner> findByCatalog(Long catalogId);
	
	public List<Partner> findAll();
	
	public void save(Partner partner) ;
	
	public Partner findById(Long id);
	
	public List<Partner> findPartnerEntries(Integer start,Integer end);
	
	public long countPartners();
	
	public void delete(Long id);
	
	public void update(Partner partner ) ;
	
	public List<Partner> findByProperty(String propertyName,Object values);
}
