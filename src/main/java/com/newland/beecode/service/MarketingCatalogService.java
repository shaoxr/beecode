package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:44:46
 * 
 */
public interface MarketingCatalogService {
	
	public List<MarketingCatalog> findAll();
	
	public List<MarketingCatalog> findMarketingCatalogEntries(Integer start,Integer end);
	
	public void save(MarketingCatalog marketingCatalog);
	
	public long countMarketingCatalogs();
	
	public void delete(Long id)throws AppException;
	
	public MarketingCatalog findById(Long id);
	
	public void update(MarketingCatalog marketingCatalog);
	
	public MarketingCatalog findMarketingCatalogsByCatalogName(String catalogName);

}
