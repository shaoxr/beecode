/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Organization;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("organizationDao")
public class OrganizationDao extends SimpleHibernateTemplate<Organization, Long> {
 
    
        public List<Organization> findOrganizationEntries(int firstResult, int maxResults) {
        //return entityManager().createQuery("select o from Organization o", Organization.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
            
                    Query query = createQuery("select o from Organization o");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);

        return query.list();
    }  
        
            public long countOrganizations() {
        //return entityManager().createQuery("select count(o) from Organization o", Long.class).getSingleResult();
                
                return this.findLong("select count(o) from Organization o");
    } 
        
}
