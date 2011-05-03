package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:48:01
 * 
 */
public interface PartnerCatalogService {
	
	public List<PartnerCatalog> findAll();
	
	public PartnerCatalog findPartnerCatalogsByCatalogName(String catalogName);
	
	public void save(PartnerCatalog partnerCatalog);
	
	public List<PartnerCatalog> findPartnerCatalogEntries(Integer start,Integer end);
	
	public long countPartnerCatalogs();
	
	public void delete(Long id)throws AppException;
	
	public PartnerCatalog findById(Long id);
	
	public void update(PartnerCatalog partnerCatalog);

}
