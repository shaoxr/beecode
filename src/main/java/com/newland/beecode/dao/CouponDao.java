package com.newland.beecode.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.MarketingAct;
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
        buf.append("select ctrl.check_day, act.act_name, pt.partner_name ,pt.partner_no,t.name,t.terminal_no,cp.coupon_id,cp.acct_mobile, "
                + "ctrl.rebate_rate,ctrl.exchange_name,abs(ctrl.exchange_amount) ,act.biz_no, (ctrl.original_amount-ctrl.off_amount),ctrl.original_amount,ctrl.back_amount,ctrl.acct_no ");
        whereBuf.append(" from coupon_ctrl ctrl,partner pt,coupon cp,marketing_act act,terminal t ");
        whereBuf.append(" where ctrl.device_no=t.terminal_no and t.partner=pt.id  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        whereBuf.append("  and (not ctrl.void_flag ='0' or ctrl.void_flag is null) and act.act_status<>"+MarketingAct.STAUS_DELETE.intValue());
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
        if (reportForm.getTerminalNo() != null) {
            buf.append(" and t.terminal_no ='" + reportForm.getTerminalNo() + "' ");
            CountBuf.append(" and t.terminal_no ='" + reportForm.getTerminalNo() + "' ");
        }
        if (reportForm.getPartnerId() != null) {
            buf.append(" and pt.id ='" + reportForm.getPartnerId() + "' ");
            CountBuf.append(" and pt.id ='" + reportForm.getPartnerId() + "' ");
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
            cd.setTerminalName((String) obj[4]);
            cd.setTerminalNo((String) obj[5]);
            cd.setCouponId(((BigInteger) obj[6]).toString());
            cd.setAcctMobile((String) obj[7]);
            cd.setRebateRate((BigDecimal) obj[8]);
            cd.setExchangeName((String) obj[9]);
            cd.setCost((BigDecimal) obj[10]);
            cd.setBizName((String) obj[11]);
            cd.setRebateAmount((BigDecimal) obj[12]);
            cd.setOriginalAmount((BigDecimal) obj[13]);
            cd.setBackAmount((BigDecimal) obj[14]);
            cd.setAcctNo((String) obj[15]);
            result.add(cd);
        }
        rr.setResultList(result);
        return rr;
    }

    public ReportResult reportCount(ReportForm reportForm) {
        StringBuffer buf = new StringBuffer();
        StringBuffer countBuf = new StringBuffer();
        StringBuffer whereBuf=new StringBuffer();

        buf.append("select act.act_name, pt.partner_name ,pt.partner_no,t.name,t.terminal_no, count(*), sum(ctrl.exchange_amount)  ,act.biz_no, sum(ctrl.original_amount) ,sum((ctrl.original_amount-ctrl.off_amount)),sum(ctrl.back_amount) ");
        countBuf.append(" select count(*) ");
        whereBuf.append(" from coupon_ctrl ctrl,partner pt,coupon cp,marketing_act act,terminal t ");
        whereBuf.append(" where ctrl.device_no=t.terminal_no and t.partner=pt.id   and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
        whereBuf.append("  and (not ctrl.void_flag ='0' or ctrl.void_flag is null) and act.act_status<>"+ MarketingAct.STAUS_DELETE.intValue());

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
        if (reportForm.getTerminalNo() != null) {
            buf.append(" and t.terminal_no ='" + reportForm.getTerminalNo() + "' ");
            countBuf.append(" and t.terminal_no ='" + reportForm.getTerminalNo() + "' ");
        }
        if (reportForm.getPartnerId() != null) {
            buf.append(" and pt.id ='" + reportForm.getPartnerId() + "' ");
            countBuf.append(" and pt.id ='" + reportForm.getPartnerId() + "' ");
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
            ps.setPartnerNo((String) obj[2]);
            ps.setTerminalName((String) obj[3]);
            ps.setTerminalNo((String) obj[4]);
            ps.setCount(((BigInteger) obj[5]).intValue());
            ps.setBizName((String) obj[7]);
            ps.setOriginalAmount((BigDecimal) obj[8]);
            ps.setExchangeAmount((BigDecimal) obj[6]);
            ps.setRebateAmount((BigDecimal) obj[9]);
            ps.setBackAmount((BigDecimal) obj[10]);
            result.add(ps);
        }
        rr.setResultList(result);
        return rr;
    }
}
