package com.newland.beecode.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.ConsumeDetail;
import com.newland.beecode.domain.report.CouponSummaryItem;
import com.newland.beecode.domain.report.PartnerSummaryItem;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import java.util.LinkedList;
import org.hibernate.Query;

@Repository("couponDao")
public class CouponDao extends SimpleHibernateTemplate<Coupon, Integer> {

    public List<Coupon> findByActNo(Long actNo) {
        return this.find("from Coupon as coupon where coupon.marketingAct.actNo= :actNo", actNo);
    }

    @SuppressWarnings("unchecked")
    public QueryResult findCouponsByCondition(CouponCondition cc) {
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        StringBuffer buf = new StringBuffer();
        StringBuffer countBuf = new StringBuffer();
        StringBuffer queryBuf = new StringBuffer();
        countBuf.append("SELECT count(coupon) FROM Coupon AS coupon ,MarketingAct as ma WHERE coupon.marketingAct.actNo=ma.actNo ");
        queryBuf.append("SELECT coupon FROM Coupon AS coupon ,MarketingAct as ma WHERE coupon.marketingAct.actNo=ma.actNo ");
        if (cc.getMinGenTime() != null && cc.getMaxGenTime() != null
                && cc.getMinGenTime().compareTo(cc.getMaxGenTime()) < 1) {
            buf.append(" and coupon.genTime >= :minGenTime and coupon.genTime <= :maxGenTime");
            conditions.put("minGenTime", cc.getMinGenTime());
            conditions.put("maxGenTime", cc.getMaxGenTime());
        }
        if (cc.getCouponId() != null) {
            buf.append(" and coupon.couponId = :couponId");
            conditions.put("couponId", cc.getCouponId());
        }
        if (cc.getCouponStatus() != null) {
            buf.append(" and coupon.couponStatus = :couponStatus");
            conditions.put("couponStatus", cc.getCouponStatus());
        }

        if (cc.getMobile() != null && cc.getMobile().length() > 0) {
            buf.append(" and coupon.acctMobile = :mobile");
            conditions.put("mobile", cc.getMobile());
        }

        if (cc.getActNo() != null) {
            buf.append(" and coupon.marketingAct.actNo = :acctNo");
            conditions.put("acctNo", cc.getActNo());
        }
        if (cc.getMarketingCatalogId() != null) {
            buf.append(" and ma.marketingCatalog.id = :id");
            conditions.put("id", cc.getMarketingCatalogId());
        }

        QueryResult qr = new QueryResult();
        Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(qr.toString());

        for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
            String cond = it.next();
            q.setParameter(cond, conditions.get(cond));
        }
        if (cc.isPagination()) {

            q = q.setFirstResult((cc.getPage() - 1) * cc.getSize());
            q = q.setMaxResults(cc.getSize());
            List result = q.list();
            qr.setResultList(result);
            for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
                String cond = it.next();
                q.setParameter(cond, conditions.get(cond));
            }
            //qr.setCount(((Long) q.getSingleResult()).intValue());
            if (!result.isEmpty()) {
                Object[] o = (Object[]) result.get(0);
                qr.setCount(Integer.valueOf(o[0].toString()));
            }

        } else {
            qr.setResultList(q.list());
        }
        return qr;
    }

    public Coupon findBySerialNo(String serialNo) {

        return findUniqueByProperty("serialNo", serialNo);
    }

    @SuppressWarnings("unchecked")
    public List<CouponSummaryItem> summaryPartnerByAct(final Long actNo){

        StringBuffer buf = new StringBuffer();
        buf.append("select pt.partner_name, ctrl.check_day, count(*), sum(act.amount) exchange, sum(ctrl.amount) ");
        buf.append("from coupon cp, marketing_act act, coupont_ctrl ctrl, marketing_act_partners mp, partner pt ");
        buf.append("where cp.marketing_act=? and cp.marketing_act=act.act_no and ctrl.coupon_id=cp.coupon_id and ");
        buf.append("mp.partners=pt.id and mp.marketing_act=act.act_no ");
        buf.append("group by pt.partner_name, ctrl.check_day");

        Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(buf.toString());

        q.setParameter(1, actNo);
        List l = q.list();
        List<CouponSummaryItem> result = new LinkedList<CouponSummaryItem>();
        for (Iterator it = l.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            CouponSummaryItem cs = new CouponSummaryItem();
            cs.setPartnerName((String) obj[0]);
            cs.setDay((Date) obj[1]);
            cs.setCount(((BigInteger) obj[2]).intValue());
            cs.setExchangeAmount((BigDecimal) obj[3]);
            cs.setRebateAmount((BigDecimal) obj[4]);
            result.add(cs);
        }

        return result;

    }

    public ReportResult reportDetail(ReportForm reportForm) {
        StringBuffer buf = new StringBuffer();
        StringBuffer CountBuf = new StringBuffer();
        HashMap<String, Object> conditions = new HashMap<String, Object>();
        buf.append("select ctrl.check_day, act.act_name, pt.partner_name ,pt.partner_no,cp.coupon_id,cp.acct_mobile, "
                + "ctrl.rebate_rate,abs(act.amount) ,bt.biz_name, (ctrl.amount*(10-ctrl.rebate_rate))/10,ctrl.amount ");
        buf.append(" from coupont_ctrl ctrl,partner pt,coupon cp,marketing_act act,business_type bt ");
        buf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        buf.append(" and bt.biz_no=act.biz_no and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");
        CountBuf.append("select count(*) ");
        CountBuf.append(" from coupont_ctrl ctrl,partner pt,coupon cp,marketing_act act,business_type bt ");
        CountBuf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        CountBuf.append(" and bt.biz_no=act.biz_no and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");

        if (reportForm.getStartDateByString() != null) {
            buf.append(" and ctrl.check_day >='" + reportForm.getStartDateByString() + "'");
            CountBuf.append(" and ctrl.check_day >='" + reportForm.getStartDateByString() + "'");
        }
        if (reportForm.getEndDate() != null) {
            buf.append(" and ctrl.check_day <='" + reportForm.getEndDateByString() + "'");
            CountBuf.append(" and ctrl.check_day <='" + reportForm.getEndDateByString() + "'");
        }
        if (reportForm.getActNo() != null) {
            buf.append(" and act.act_no = '" + reportForm.getActNo() + "'");
            CountBuf.append(" and act.act_no = '" + reportForm.getActNo() + "'");
        }
        if (reportForm.getParterNo() != null) {
            buf.append(" and pt.partner_no ='" + reportForm.getParterNo() + "' ");
            CountBuf.append(" and pt.partner_no ='" + reportForm.getParterNo() + "' ");
        }
        if (reportForm.getPartnerCatalogId() != null) {
            buf.append(" and pt.partner_catalog ='" + reportForm.getPartnerCatalogId() + "' ");
            CountBuf.append(" and pt.partner_catalog ='" + reportForm.getPartnerCatalogId() + "' ");
        }
        if (reportForm.getMarketingCatalogId() != null) {
            buf.append(" and act.marketing_catalog = '" + reportForm.getMarketingCatalogId() + "'");
            CountBuf.append(" and act.marketing_catalog = '" + reportForm.getMarketingCatalogId() + "'");
        }
        buf.append(" order by ctrl.check_day,act.act_name, pt.partner_name ");

        Query countQ = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(CountBuf.toString());

        ReportResult rr = new ReportResult();
        rr.setCount(0);

        for (Iterator it = countQ.list().iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            rr.setCount(Long.valueOf(obj[0].toString()));
            break;
        }


        Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(buf.toString());

        List list;
        if (reportForm.isPagination()) {
            list = q.setFirstResult((reportForm.getPage() - 1) * reportForm.getSize()).setMaxResults(reportForm.getSize()).list();
        } else {
            list = q.list();
        }
        List result = new ArrayList();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();

            ConsumeDetail cd = new ConsumeDetail();
            cd.setCheckDay((Date) obj[0]);
            cd.setActName((String) obj[1]);
            cd.setParterName((String) obj[2]);
            cd.setParterNo((String) obj[3]);
            cd.setCouponId(((BigInteger) obj[4]).toString());
            cd.setAcctMobile((String) obj[5]);
            cd.setRebateRate((BigDecimal) obj[6]);
            cd.setCost((BigDecimal) obj[7]);
            cd.setBizName((String) obj[8]);
            cd.setRebateAmount((BigDecimal) obj[9]);
            cd.setOriginalAmount((BigDecimal) obj[10]);
            //PartnerSummaryItem ps = new PartnerSummaryItem();
            //ps.setCheckDay((Date)obj[0]);
            //ps.setActName((String)obj[1]);
            //ps.setPartnerName((String)obj[2]);
            //ps.setBizName((String)obj[4]);
            //ps.setExchangeAmount((BigDecimal)obj[3]);
            //ps.setRebateAmount((BigDecimal)obj[5]);
            result.add(cd);
        }
        rr.setResultList(result);
        return rr;
    }

    public ReportResult reportCount(ReportForm reportForm) {
        StringBuffer buf = new StringBuffer();
        StringBuffer countBuf = new StringBuffer();

        HashMap<String, Object> conditions = new HashMap<String, Object>();
        buf.append("select act.act_name, pt.partner_name , count(*), sum(act.amount)  ,bt.biz_name, sum(ctrl.amount) ,sum((ctrl.amount*(10-ctrl.rebate_rate))/10) ");
        buf.append(" from coupont_ctrl ctrl,partner pt,coupon cp,marketing_act act,business_type bt ");
        buf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        buf.append(" and bt.biz_no=act.biz_no and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");

        if (reportForm.getStartDateByString() != null) {
            buf.append(" and ctrl.check_day >='" + reportForm.getStartDateByString() + "'");
        }
        if (reportForm.getEndDate() != null) {
            buf.append(" and ctrl.check_day <='" + reportForm.getEndDateByString() + "'");
        }
        if (reportForm.getActNo() != null) {
            buf.append(" and act.act_no = '" + reportForm.getActNo() + "'");
        }
        if (reportForm.getParterNo() != null) {
            buf.append(" and pt.partner_no ='" + reportForm.getParterNo() + "' ");
        }
        if (reportForm.getPartnerCatalogId() != null) {
            buf.append(" and pt.partner_catalog ='" + reportForm.getPartnerCatalogId() + "' ");
        }
        if (reportForm.getMarketingCatalogId() != null) {
            buf.append(" and act.marketing_catalog = '" + reportForm.getMarketingCatalogId() + "'");
        }
        buf.append(" group by act.act_name, pt.partner_name ");

        Query q = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(buf.toString());

        ReportResult rr = new ReportResult();
        rr.setCount(q.list().size());
        List list;
        if (reportForm.isPagination()) {
            list = q.setFirstResult((reportForm.getPage() - 1) * reportForm.getSize()).setMaxResults(reportForm.getSize()).list();
        } else {
            list = q.list();
        }
        List result = new ArrayList();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            PartnerSummaryItem ps = new PartnerSummaryItem();
            ps.setActName((String) obj[0]);
            ps.setPartnerName((String) obj[1]);
            ps.setCount(((BigInteger) obj[2]).intValue());
            ps.setBizName((String) obj[4]);
            ps.setOriginalAmount((BigDecimal) obj[5]);
            ps.setExchangeAmount((BigDecimal) obj[3]);
            ps.setRebateAmount((BigDecimal) obj[6]);
            result.add(ps);
        }
        rr.setResultList(result);
        return rr;
    }
}
