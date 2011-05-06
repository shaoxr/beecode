/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Oper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("operDao")
public class OperDao extends SimpleHibernateTemplate<Oper, Long> {
	
    public List<Oper> findOperEntries(int firstResult, int maxResults) {
        return findListByQuery("select o from Oper o", firstResult, maxResults);
    }

    public long countOpers() {
        return this.findLong("select count(o) from Oper o");
    }

	public List<Oper> findOperByOperName(String operName) {
	   if (operName == null || operName.trim().length() == 0) {
            throw new IllegalArgumentException("The operName argument is required");
       }
	   return this.find("FROM Oper AS oper WHERE oper.operName = ?", operName);
	    
	}
}
