/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.PartnerCatalog;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("partnerCatalogDao")
public class PartnerCatalogDao extends SimpleHibernateTemplate<PartnerCatalog, Long> {

    public  long countPartnerCatalogs() {
        return this.findLong("select count(o) from PartnerCatalog o");
    } 
}
