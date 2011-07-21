package com.newland.beecode.service;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.newland.beecode.domain.Terminal;
import com.newland.beecode.exception.AppException;

/**
 * @author shaoxr:
 * @version 2011-7-3 下午02:25:00
 * 
 */
public interface TerminalService {
	
	public Terminal save(Terminal terminal)throws AppException;
	
	public Terminal findById(Long id);
	
	public List<Terminal> findTerminalEntries(Integer start,Integer size);
	
	public long countTerminals();
	
	public void delete(Long id)throws AppException;
	
	public void update(Terminal terminal)throws AppException;
	
	public List<Terminal> findByPartnerEntries(Long id,Integer fist,Integer size);
	
	public long countTerminalByPartner(Long id);
	
	public List<Terminal> findByPartner(Long id);
	
	public Terminal findByTerminalNo(String terminalNo);
	
	public void save(Set<Terminal> terminals);
	
	public int importTerminal(MultipartFile partnerFile)throws AppException;

}
