package com.newland.beecode.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.beecode.dao.PartnerCatalogDao;
import com.newland.beecode.dao.PartnerDao;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.PartnerCatalogService;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:46:07
 * 
 */
@Service(value = "partnerCatalogService")
public class PartnerCatalogServiceImpl implements PartnerCatalogService{
	@Resource(name="partnerCatalogDao")
    private PartnerCatalogDao partnerCatalogDao;
	@Resource(name="partnerDao")
	private PartnerDao partnerDao;
	@Override
	public List<PartnerCatalog> findAll() {
		return this.partnerCatalogDao.findAll();
	}
	@Override
	public PartnerCatalog findPartnerCatalogsByCatalogName(String catalogName) {
		List<PartnerCatalog> list= this.partnerCatalogDao.findPartnerCatalogsByCatalogName(catalogName);
		return list.size()>0?list.get(0):null;
	}
	@Override
	public void save(PartnerCatalog partnerCatalog) {
		this.partnerCatalogDao.save(partnerCatalog);
		
	}
	@Override
	public List<PartnerCatalog> findPartnerCatalogEntries(Integer start,
			Integer end) {
		return this.partnerCatalogDao.findPartnerCatalogEntries(start, end);
	}
	@Override
	public long countPartnerCatalogs() {
		return this.partnerCatalogDao.countPartnerCatalogs();
	}
	@Override
	public void delete(Long id) throws AppException{
		List<Partner> partners=this.partnerDao.findByProperty("partnerCatalog.id", id);
		if(partners.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNERCATALOG_DONOT_DELETE,"");
		}
		this.partnerCatalogDao.delete(id);
		
	}
	@Override
	public PartnerCatalog findById(Long id) {
		return this.partnerCatalogDao.get(id);
	}
	@Override
	public void update(PartnerCatalog partnerCatalog) {
		this.partnerCatalogDao.update(partnerCatalog);
	}

}
