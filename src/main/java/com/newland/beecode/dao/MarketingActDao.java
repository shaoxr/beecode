/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.MarketingActSummary;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("marketingActDao")
public class MarketingActDao extends BaseDao<MarketingAct, Long> {

    public QueryResult findMarketingActEntriesByActStatus(Integer actStatus, Integer page, Integer size) {


        QueryResult qr = new QueryResult();

        //List<MarketingAct> list = this.find("from MarketingAct ma where ma.actStatus=?", actStatus);
        List<MarketingAct> list =this.findListByQuery("from MarketingAct ma where ma.actStatus=?", page, size, actStatus);

        List counts = this.find("select count(ma) from MarketingAct ma where ma.actStatus=?", actStatus);

        if (!counts.isEmpty()) {

        	Long o = (Long) counts.get(0);
            qr.setCount(o.intValue());
        }

        qr.setResultList(list);
        return qr;



    }
    public QueryResult _findMarketingActsByCondition(MarketingActCondition mac) {
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        StringBuffer queryBuf = new StringBuffer();
        StringBuffer buf = new StringBuffer();
        StringBuffer countBuf = new StringBuffer();
        countBuf.append("SELECT count(*) FROM MarketingAct AS marketingact WHERE 1=1");
        queryBuf.append(" FROM MarketingAct AS marketingact WHERE 1=1  ");
        if (mac.getStartGenDate() != null && mac.getEndGenDate() != null) {
            buf.append(" and  marketingact.genTime >= "+mac.getStartGenDate()+" and marketingact.genTime <= :maxGenTime");
            conditions.put("minGenTime", mac.getStartGenDate());
            conditions.put("maxGenTime", mac.getEndGenDate());
        }
        if (mac.getBizNo() != null) {
            buf.append(" and marketingact.bizNo = :bizNo");
            conditions.put("bizNo", mac.getBizNo());
        }
        if (mac.getActName() != null && mac.getActName().length() > 0) {

            buf.append(" and LOWER(marketingact.actName) LIKE LOWER(:actName)");
            conditions.put("actName", "%" + mac.getActName() + "%");
        }
        if (mac.getActStatus() != null) {
            buf.append(" and marketingact.actStatus = :actStatus");
            conditions.put("actStatus", mac.getActStatus());
        }
        if (mac.getMarketingCatalogId() != null) {
            buf.append(" and marketingact.marketingCatalog.id = :catalogId");
            conditions.put("catalogId", mac.getMarketingCatalogId());
        }
        QueryResult qr = new QueryResult();

        Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(countBuf.append(buf).toString());

        for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
            String cond = it.next();
            q.setParameter(cond, conditions.get(cond));
        }

        List list = q.list();
        if (!list.isEmpty()) {
            Object[] obj = (Object[]) list.get(0);
            Long count = new Long((String) obj[0]);
            qr.setCount(count.intValue());
        }

        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(countBuf.append(buf).toString());

        
        for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
            String cond = it.next();
            query.setParameter(cond, conditions.get(cond));
        }
       // if (mac.isPagination()) {
        //    qr.setResultList(query.setFirstResult((mac.getPage() - 1) * mac.getSize()).setMaxResults(mac.getSize()).list());
       //     this.findListByQuery(hql, firstReult, maxResult)
      //  } else {
      //      qr.setResultList(query.list());
      //  }

        return qr;
    }

    public void expired(Date date) {
        excuteByHQL("update MarketingAct AS act set act.actStatus =:status where act.endDate<:edate", MarketingAct.STATUS_EXPIRED, date);
    }

    public MarketingActSummary marketingSummary(Long actNo) {

       String sql="select sum(remain_times) remainTimes, sum(times-remain_times) usedTimes, count(marketing_act) joinCount from coupon where marketing_act="+actNo;
       List<Object[]> list=this.findBySql(sql);
        if (!list.isEmpty()) {
            Object[] obj = (Object[]) list.get(0);
            MarketingActSummary mas = new MarketingActSummary();
            mas.setRemainTimes(((BigDecimal) obj[0]).intValue());
            mas.setUsedTimes(((BigDecimal) obj[1]).intValue());
            mas.setJoinCount(((BigInteger) obj[2]).intValue());
            return mas;
        }
        return null;
    }

    public List<MarketingAct> findByCatalog(Long id) {
        return find(" from  MarketingAct marketingAct where marketingAct.marketingCatalog.id=?", id);
    }

    public List<MarketingAct> findMarketingActEntries(int firstResult, int maxResults) {

        
                return findListByQuery("select o from MarketingAct o", firstResult, maxResults);

    }

    public long countMarketingActs() {
        //return entityManager().createQuery("select count(o) from MarketingAct o", Long.class).getSingleResult();

        return this.findLong("select count(o) from MarketingAct o");

    }

    public List<MarketingAct> findMarketingActsByActStatus(Integer actStatus) {
        if (actStatus == null) {
            throw new IllegalArgumentException("The actStatus argument is required");
        }

        return find("SELECT marketingact FROM MarketingAct AS marketingact WHERE marketingact.actStatus =?", actStatus);
    }

    public MarketingAct findMarketingAct(Long id) {
        if (id == null) {
            return null;
        }
        return this.findUniqueByProperty("id", id);
    }
}
