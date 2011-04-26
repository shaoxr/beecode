/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Oper;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("operDao")
public class OperDao extends SimpleHibernateTemplate<Oper, Long> {
        public List<MarketingAct> findOperEntries(int firstResult, int maxResults) {

        Query query = createQuery("select o from Oper o");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.list();

    }

    public long countOpers() {
        return this.findLong("select count(o) from Oper o");

    }
}
