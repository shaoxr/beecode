package com.newland.beecode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.beecode.dao.MarketingActDao;
import com.newland.beecode.dao.PartnerCatalogDao;
import com.newland.beecode.dao.PartnerDao;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.PartnerService;
import javax.annotation.Resource;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

    @Resource(name = "partnerDao")
    private PartnerDao partnerDao;
    @Resource(name = "partnerCatalogDao")
    private PartnerCatalogDao partnerCatalogDao;
    @Autowired
    private MarketingActDao marketingActDao;


	@Override
	public List<Partner> findByCatalog(Long catalogId) {
		if(PartnerCatalog.ALL_LONG.equals(catalogId)){
			return this.partnerDao.find("from Partner p ");
		}
		return this.partnerDao.findByProperty("partnerCatalog.id", catalogId);
	}

	@Override
	public List<Partner> findAll() {
		return this.partnerDao.findAll();
	}

	@Override
	public void save(Partner partner) throws AppException{
		Partner _partner =this.partnerDao.findUniqueByProperty("partnerNo", partner.getPartnerNo());
		if(_partner!=null){
			throw new AppException(ErrorsCode.BIZ_PARTNER_NO_EXITS,"");
		}
		_partner=this.partnerDao.findUniqueByProperty("partnerName", partner.getPartnerName());
		if(_partner!=null){
			throw new AppException(ErrorsCode.BIZ_PARTNER_NAME_EXITS,"");
		}
		//PartnerCatalog partnerCatalog=this.partnerCatalogDao.get(partner.getPartnerCatalog().getId());
		//partner.setPartnerCatalog(partnerCatalog);
		this.partnerDao.save(partner);
		
	}

	@Override
	public Partner findById(Long id) {
		return this.partnerDao.get(id);
	}

	@Override
	public List<Partner> findPartnerEntries(Integer start, Integer end) {
		return this.partnerDao.findListByQuery("select o from Partner o", start, end);
	}

	@Override
	public long countPartners() {
		return this.partnerDao.findLong("select count(o) from Partner o");
	}

	@Override
	@Transactional
	public void delete(Long id) throws AppException{
		List<MarketingAct> acts=this.marketingActDao.find("select  act from MarketingAct act join act.partners as ps where ps.id=?", id);
		if(acts.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNER_DO_NOT_DELETE,"");
		}
		this.partnerDao.delete(id);
		
	}

	@Override
	public void update(Partner partner) throws AppException{
		
		
		List<Partner> partners=this.partnerDao.find("from Partner p where p.partnerName=? and p.id<>?", partner.getPartnerName(),partner.getId());
		if(partners.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNER_NAME_EXITS,"");
		}
		partners=this.partnerDao.find("from Partner p where  p.partnerNo=? and p.id<>?",partner.getPartnerNo(),partner.getId());
		PartnerCatalog partnerCatalog=this.partnerCatalogDao.get(partner.getPartnerCatalog().getId());
		if(partners.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNER_NO_EXITS,"");
		}
		partner.setPartnerCatalog(partnerCatalog);
		this.partnerDao.update(partner);
		
	}

	@Override
	public List<Partner> findByProperty(String propertyName, Object value) {
		return this.partnerDao.findByProperty(propertyName, value);
	}

	@Override
	public List<Partner> findByCatalogEntries(Long partnerCatalogId,
			Integer start, Integer end) {
		if(partnerCatalogId.equals(PartnerCatalog.ALL_LONG)){
			return this.partnerDao.findListByQuery("from Partner p ", start, end);
		}
		return this.partnerDao.findListByQuery("from Partner p where p.partnerCatalog.id=?", start, end, partnerCatalogId);
	}

	@Override
	public long countPartnersByCatalog(Long partnerCatalogId) {
		if(partnerCatalogId.equals(PartnerCatalog.ALL_LONG)){
			return this.partnerDao.findLong("select count(o) from Partner o");
		}
		return this.partnerDao.findLong("select count(*) from Partner p where p.partnerCatalog.id=?", partnerCatalogId);
	}

	@Override
	public Partner findByPartnerNo(String partnerNo) {
		List<Partner> partners=this.partnerDao.find("from Partner p where p.partnerNo=?", partnerNo);
		return partners.size()>0?partners.get(0):null;
	}
}
