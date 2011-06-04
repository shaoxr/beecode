/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Partner;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("partnerDao")
public class PartnerDao extends SimpleHibernateTemplate<Partner, Long>{
    
    
}
