package com.newland.beecode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.beecode.dao.MarketingCatalogDao;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.service.MarketingCatalogService;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:51:38
 * 
 */
@Service(value="marketingCatalogService")
public class MarketingCatalogServiceImpl implements MarketingCatalogService{
    @Resource(name="marketingCatalogDao")
	private MarketingCatalogDao marketingCatalogDao;
	@Override
	public List<MarketingCatalog> findAll() {
		return this.marketingCatalogDao.findAll();
	}
	@Override
	public List<MarketingCatalog> findMarketingCatalogEntries(Integer page,
			Integer size) {
		return null;
	}

}
