/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Oper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("operDao")
public class OperDao extends SimpleHibernateTemplate<Oper, Long> {
	

    public long countOpers() {
        return this.findLong("select count(o) from Oper o");
    }
}
