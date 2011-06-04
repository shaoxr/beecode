package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.exception.AppException;

public interface PartnerService {

	
	public List<Partner> findByCatalog(Long catalogId);
	
	public List<Partner> findAll();
	
	public void save(Partner partner) throws AppException;
	
	public Partner findById(Long id);
	
	public List<Partner> findPartnerEntries(Integer start,Integer end);
	
	public List<Partner> findByCatalogEntries(Long partnerCatalogId,Integer start,Integer end);
	
	public long countPartners();
	
	public long countPartnersByCatalog(Long partnerCatalogId);
	
	public void delete(Long id)throws AppException;
	
	public void update(Partner partner )throws AppException ;
	
	public List<Partner> findByProperty(String propertyName,Object values);
	
	public Partner findByPartnerNo(String partnerNo);
}
