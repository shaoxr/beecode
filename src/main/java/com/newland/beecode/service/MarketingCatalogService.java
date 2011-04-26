package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.MarketingCatalog;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:44:46
 * 
 */
public interface MarketingCatalogService {
	
	public List<MarketingCatalog> findAll();
	
	public List<MarketingCatalog> findMarketingCatalogEntries(Integer page,Integer size);

}
