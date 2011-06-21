package com.newland.beecode.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.report.ConsumeDetail;
import com.newland.beecode.domain.report.PartnerSummaryItem;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;

@Repository("couponDao")
public class CouponDao extends BaseDao<Coupon,Long> {

    public List<Coupon> findByActNo(Long actNo) {
        return this.find("from Coupon as coupon where coupon.marketingAct.actNo= ?", actNo);
    }


    public Coupon findBySerialNo(String serialNo) {

        return findUniqueByProperty("serialNo", serialNo);
    }


    public ReportResult reportDetail(ReportForm reportForm) {
        StringBuffer buf = new StringBuffer();
        StringBuffer CountBuf = new StringBuffer();
        StringBuffer whereBuf=new StringBuffer();
        buf.append("select ctrl.check_day, act.act_name, pt.partner_name ,pt.partner_no,cp.coupon_id,cp.acct_mobile, "
                + "ctrl.rebate_rate,abs(ctrl.amount) ,act.biz_no, (ctrl.original_amount-ctrl.off_amount),ctrl.original_amount,ctrl.back_amount ");
        whereBuf.append(" from coupon_ctrl ctrl,partner pt,coupon cp,marketing_act act ");
        whereBuf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        whereBuf.append("  and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");
        CountBuf.append("select count(*) ");
        buf=buf.append(whereBuf);
        CountBuf.append(whereBuf);
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


        ReportResult rr = new ReportResult();
        rr.setCount(0);
        List<Object[]> list;
        if (reportForm.isPagination()) {
            list = this.findBySqlByLimit(buf.toString(), (reportForm.getPage() - 1) * reportForm.getSize(), reportForm.getSize());
            rr.setCount(this.countBySql(CountBuf.toString()));
        } else {
            list = this.findBySql(buf.toString());
        }
        List<ConsumeDetail> result = new ArrayList<ConsumeDetail>();
        for (Iterator<Object[]> it = list.iterator(); it.hasNext();) {
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
            cd.setBackAmount((BigDecimal) obj[11]);
            result.add(cd);
        }
        rr.setResultList(result);
        return rr;
    }

    public ReportResult reportCount(ReportForm reportForm) {
        StringBuffer buf = new StringBuffer();
        StringBuffer countBuf = new StringBuffer();
        StringBuffer whereBuf=new StringBuffer();

        buf.append("select act.act_name, pt.partner_name , count(*), sum(ctrl.amount)  ,act.biz_no, sum(ctrl.original_amount) ,sum((ctrl.original_amount-ctrl.off_amount)),sum(ctrl.back_amount) ");
        countBuf.append(" select count(*) ");
        whereBuf.append(" from coupon_ctrl ctrl,partner pt,coupon cp,marketing_act act ");
        whereBuf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        whereBuf.append("  and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");

        buf.append(whereBuf);
        countBuf.append(whereBuf);
        if (reportForm.getStartDateByString() != null) {
            buf.append(" and ctrl.check_day >='" + reportForm.getStartDateByString() + "'");
            countBuf.append(" and ctrl.check_day >='" + reportForm.getStartDateByString() + "'");
        }
        if (reportForm.getEndDate() != null) {
            buf.append(" and ctrl.check_day <='" + reportForm.getEndDateByString() + "'");
            countBuf.append(" and ctrl.check_day <='" + reportForm.getEndDateByString() + "'");
        }
        if (reportForm.getActNo() != null) {
            buf.append(" and act.act_no = '" + reportForm.getActNo() + "'");
            countBuf.append(" and act.act_no = '" + reportForm.getActNo() + "'");
        }
        if (reportForm.getParterNo() != null) {
            buf.append(" and pt.partner_no ='" + reportForm.getParterNo() + "' ");
            countBuf.append(" and pt.partner_no ='" + reportForm.getParterNo() + "' ");
        }
        if (reportForm.getPartnerCatalogId() != null) {
            buf.append(" and pt.partner_catalog ='" + reportForm.getPartnerCatalogId() + "' ");
            countBuf.append(" and pt.partner_catalog ='" + reportForm.getPartnerCatalogId() + "' ");
        }
        if (reportForm.getMarketingCatalogId() != null) {
            buf.append(" and act.marketing_catalog = '" + reportForm.getMarketingCatalogId() + "'");
            countBuf.append(" and act.marketing_catalog = '" + reportForm.getMarketingCatalogId() + "'");
        }
        buf.append(" group by act.act_name, pt.partner_name ");


        ReportResult rr = new ReportResult();
        List<Object[]> list;
        if (reportForm.isPagination()) {
            list = this.findBySqlByLimit(buf.toString(), (reportForm.getPage() - 1) * reportForm.getSize(), reportForm.getSize());
            rr.setCount(this.countBySql(countBuf.toString()));
        } else {
            list = this.findBySql(buf.toString());
        }
        List<PartnerSummaryItem> result = new ArrayList<PartnerSummaryItem>();
        for (Iterator<Object[]> it = list.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            PartnerSummaryItem ps = new PartnerSummaryItem();
            ps.setActName((String) obj[0]);
            ps.setPartnerName((String) obj[1]);
            ps.setCount(((BigInteger) obj[2]).intValue());
            ps.setBizName((String) obj[4]);
            ps.setOriginalAmount((BigDecimal) obj[5]);
            ps.setExchangeAmount((BigDecimal) obj[3]);
            ps.setRebateAmount((BigDecimal) obj[6]);
            ps.setBackAmount((BigDecimal) obj[7]);
            result.add(ps);
        }
        rr.setResultList(result);
        return rr;
    }
}
