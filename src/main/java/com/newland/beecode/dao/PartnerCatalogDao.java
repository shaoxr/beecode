/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.PartnerCatalog;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("partnerCatalogDao")
public class PartnerCatalogDao extends SimpleHibernateTemplate<PartnerCatalog, Integer> {

    public List<PartnerCatalog> findPartnerCatalogsByCatalogName(String catalogName) {
        if (catalogName == null || catalogName.length() == 0) {
            throw new IllegalArgumentException("The catalogName argument is required");
        }

        return this.find("SELECT PartnerCatalog FROM PartnerCatalog AS partnercatalog WHERE partnercatalog.catalogName = :catalogName", catalogName);
    }
}
