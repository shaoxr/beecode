package com.newland.beecode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.beecode.dao.PartnerDao;
import com.newland.beecode.dao.TerminalDao;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.Terminal;
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

	@Override
	public Terminal save(Terminal terminal) {
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
	public void delete(Long id) {
		this.terminalDao.delete(id);
		
	}

	@Override
	public void update(Terminal terminal) {
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

}
