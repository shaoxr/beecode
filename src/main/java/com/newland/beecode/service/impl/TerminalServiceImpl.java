package com.newland.beecode.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.beecode.dao.MarketingActDao;
import com.newland.beecode.dao.PartnerDao;
import com.newland.beecode.dao.TerminalDao;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.Terminal;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.TerminalService;

/**
 * @author shaoxr:
 * @version 2011-7-3 下午02:25:14
 * 
 */
@Service(value="terminalService")
public class TerminalServiceImpl implements TerminalService{
	@Autowired
	private TerminalDao terminalDao;
	@Autowired
	private PartnerDao partnerDao;
	@Autowired
    private MarketingActDao marketingActDao;

	@Override
	public Terminal save(Terminal terminal)throws AppException {
		Terminal _terminal =this.terminalDao.findUniqueByProperty("terminalNo", terminal.getTerminalNo());
		if(_terminal!=null){
			throw new AppException(ErrorsCode.BIZ_PARTNER_TERMINAL_NO_EXITS,"");
		}
		_terminal=this.terminalDao.findUniqueByProperty("name", terminal.getName());
		if(_terminal!=null){
			throw new AppException(ErrorsCode.BIZ_PARTNER_TERMINAL_NAME_EXITS,"");
		}
		Partner partner=this.partnerDao.get(terminal.getPartner().getId());
		terminal.setPartner(partner);
		return this.terminalDao.save(terminal);
	}

	@Override
	public Terminal findById(Long id) {
		return this.terminalDao.get(id);
	}

	@Override
	public List<Terminal> findTerminalEntries(Integer start, Integer size) {
		return this.terminalDao.findListByQuery("from Terminal t ", start, size);
	}

	@Override
	public long countTerminals() {
		return this.terminalDao.findLong("select count(*) from Terminal t");
	}

	@Override
	public void delete(Long id) throws AppException{
		List<MarketingAct> acts=this.marketingActDao.find("select  act from MarketingAct act join act.terminals as t where t.id=? and act.actStatus<=? ", id,MarketingAct.STATUS_AFTER_GIVE);
		if(acts.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNER_TERMINAL_DO_NOT_DELETE,"");
		}
		this.terminalDao.excuteByHQL("delete from Terminal t where t.id=?", id);
		
	}

	@Override
	public void update(Terminal terminal)throws  AppException{
		List<Terminal> terminals=this.terminalDao.find("from Terminal t where t.name=? and t.id<>?", terminal.getName(),terminal.getId());
		if(terminals.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNER_TERMINAL_NAME_EXITS,"");
		}
		terminals=this.terminalDao.find("from Terminal t where  t.terminalNo=? and t.id<>?",terminal.getTerminalNo(),terminal.getId());
		if(terminals.size()>0){
			throw new AppException(ErrorsCode.BIZ_PARTNER_TERMINAL_NO_EXITS,"");
		}
		Partner partner=this.partnerDao.get(terminal.getPartner().getId());
		terminal.setPartner(partner);
		this.terminalDao.update(terminal);
		
	}

	@Override
	public List<Terminal> findByPartnerEntries(Long id,Integer fist,Integer size) {
		if(Partner.ALL_LONG.equals(id)){
			return this.terminalDao.findListByQuery("from Terminal t", fist, size);
		}
		return this.terminalDao.findListByQuery("from Terminal t where t.partner.id=?", fist, size, id);
	}

	@Override
	public long countTerminalByPartner(Long id) {
		return this.terminalDao.findLong("select count(*) from Terminal t where t.partner.id=?", id);
	}

	@Override
	public List<Terminal> findByPartner(Long id) {
		if(Partner.ALL_LONG.equals(id)){
			return this.terminalDao.find("from Terminal t");
		}
		return this.terminalDao.find("from Terminal t where t.partner.id=?", id);
	}

	@Override
	public Terminal findByTerminalNo(String terminalNo) {
		return this.terminalDao.findUniqueByProperty("terminalNo", terminalNo);
	}

	@Override
	public void save(Set<Terminal> terminals) {
		for(Terminal terminal :terminals){
			this.terminalDao.saveOrUpdate(terminal);
		}
		
	}

}
