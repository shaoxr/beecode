/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.report.PartnerSummaryItem;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("partnerDao")
public class PartnerDao extends SimpleHibernateTemplate<Partner, Long>{
    
    
       public  List<PartnerSummaryItem> summaryPartner(Long id) {
        StringBuffer buf = new StringBuffer();
        buf.append("select pt.partner_name, act_name, bt.biz_name,check_day,count(*),sum(act.amount) exchange, sum(ctrl.amount) ");
        buf.append("from partner pt, marketing_act act, coupon cp, coupont_ctrl ctrl, marketing_act_partners ap , business_type bt ");
        buf.append("where pt.id=? and pt.id=ap.partners and act.act_no=ap.marketing_act and act.act_status>3 ");
        buf.append("and cp.marketing_act=act.act_no and cp.coupon_id=ctrl.coupon_id and bt.biz_no=act.biz_no ");
        buf.append("group by pt.partner_name, act_name, check_day,bt.biz_name");

        Query q = this.getSessionFactory().getCurrentSession().createQuery(buf.toString());
        q.setParameter(1, id);
        List l = q.list();
        List<PartnerSummaryItem> items = new ArrayList<PartnerSummaryItem>();
        for (Iterator it = l.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            PartnerSummaryItem item = new PartnerSummaryItem();
            item.setPartnerName((String) obj[0]);
            item.setActName((String) obj[1]);
            item.setBizName((String) obj[2]);
            item.setCheckDay((Date) obj[3]);
            item.setCount(((BigInteger) obj[4]).intValue());
            item.setExchangeAmount((BigDecimal) obj[5]);
            item.setRebateAmount((BigDecimal) obj[6]);
            items.add(item);
        }
        return items;
    }

    public  List<Partner> findByCatalog(Long id) {
        return this.find("from  Partner partner where partner.partnerCatalog.id=?", id);
    }
    
    public  List<Partner> findPartnerEntries(int firstResult, int maxResults) {
       return findListByQuery("select o from Partner o", firstResult, maxResults);
    } 
    
    public long countPartners() {
        return this.findLong("select count(o) from Partner o");
          
    }
}
