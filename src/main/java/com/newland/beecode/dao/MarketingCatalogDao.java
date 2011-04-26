package com.newland.beecode.dao;

import org.springframework.stereotype.Repository;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.MarketingCatalog;
import java.util.List;
import org.hibernate.Query;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:53:18
 * 
 */
@Repository("marketingCatalogDao")
public class MarketingCatalogDao extends SimpleHibernateTemplate<MarketingCatalog, Long> {

    public List<MarketingCatalog> findMarketingCatalogEntries(int firstResult, int maxResults) {


        return findListByQuery("select o from MarketingCatalog o", firstResult, maxResults);

    }

    public long countMarketingCatalogs() {
        return this.findLong("select count(o) from MarketingCatalog o");

    }
}
