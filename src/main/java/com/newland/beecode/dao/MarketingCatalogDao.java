package com.newland.beecode.dao;

import org.springframework.stereotype.Repository;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.MarketingCatalog;

/**
 * @author shaoxr:
 * @version 2011-4-25 下午12:53:18
 * 
 */
@Repository("marketingCatalogDao")
public class MarketingCatalogDao extends SimpleHibernateTemplate<MarketingCatalog, Integer>{

}
