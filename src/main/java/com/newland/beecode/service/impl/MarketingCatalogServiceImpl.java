package com.newland.beecode.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.beecode.dao.MarketingActDao;
import com.newland.beecode.dao.MarketingCatalogDao;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
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
    @Resource(name="marketingActDao")
    private MarketingActDao marketingActDao;
	@Override
	public List<MarketingCatalog> findAll() {
		return this.marketingCatalogDao.findAll();
	}
	@Override
	public List<MarketingCatalog> findMarketingCatalogEntries(Integer start,
			Integer end) {
		return this.marketingCatalogDao.findMarketingCatalogEntries(start, end);
	}
	@Override
	public void save(MarketingCatalog marketingCatalog) {
		marketingCatalog.setCreateTime(new Date());
		marketingCatalog.setUpdateTime(new Date());
		this.marketingCatalogDao.save(marketingCatalog);
		
	}
	@Override
	public long countMarketingCatalogs() {
		return this.marketingCatalogDao.countMarketingCatalogs();
	}
	@Override
	public void delete(Long id)throws AppException {
		List<MarketingAct> marketingActs=this.marketingActDao.findByProperty("marketingCatalog.id", id);
		if(marketingActs.size()>0){
			throw new AppException(ErrorsCode.BIZ_MARKINGCATALOG_DONOT_DELETE,"");
		}
		this.marketingCatalogDao.delete(id);
		
	}
	@Override
	public MarketingCatalog findById(Long id) {
		return this.marketingCatalogDao.get(id);
	}
	@Override
	public void update(MarketingCatalog marketingCatalog) {
		this.marketingCatalogDao.update(marketingCatalog);
	}
	@Override
	public MarketingCatalog findMarketingCatalogsByCatalogName(
			String catalogName) {
		List<MarketingCatalog> list= this.marketingCatalogDao.findMarketingCatalogsByCatalogName(catalogName);
		return list.size()>0?list.get(0):null;
	}

}
