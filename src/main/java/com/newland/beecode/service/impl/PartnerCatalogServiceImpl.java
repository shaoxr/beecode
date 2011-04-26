package com.newland.beecode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.beecode.dao.PartnerCatalogDao;
import com.newland.beecode.domain.PartnerCatalog;
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
	@Override
	public List<PartnerCatalog> findAll() {
		return this.partnerCatalogDao.findAll();
	}

}
