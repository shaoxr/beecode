package com.newland.beecode.service;

import java.util.List;

import com.newland.beecode.domain.Terminal;

/**
 * @author shaoxr:
 * @version 2011-7-3 下午02:25:00
 * 
 */
public interface TerminalService {
	
	public Terminal save(Terminal terminal);
	
	public Terminal findById(Long id);
	
	public List<Terminal> findTerminalEntries(Integer start,Integer size);
	
	public long countTerminals();
	
	public void delete(Long id);
	
	public void update(Terminal terminal);
	
	public List<Terminal> findByPartnerEntries(Long id,Integer fist,Integer size);
	
	public long countTerminalByPartner(Long id);
	
	public List<Terminal> findByPartner(Long id);
	
	public Terminal findByTerminalNo(String terminalNo);

}
