package com.newland.beecode.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intensoft.dao.hibernate.SimpleQueryCondition;
import com.newland.beecode.dao.CouponCtrlDao;
import com.newland.beecode.dao.CouponDao;
import com.newland.beecode.domain.Coupon;
import com.newland.beecode.domain.CoupontCtrl;
import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.CouponSumaryReport;
import com.newland.beecode.domain.report.CouponSumaryReport.CouponSummaryDayItem;
import com.newland.beecode.domain.report.CouponSumaryReport.CouponSummaryPartnerItem;
import com.newland.beecode.domain.report.CouponSummaryItem;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.MarketingActService;
import com.newland.utils.UuidHelper;
import javax.annotation.Resource;

@Service("couponService")
public class CouponServiceImpl implements CouponService {

    @Resource(name = "couponDao")
    private CouponDao couponDao;
    
    @Resource(name = "couponCtlDao")
    private CouponCtrlDao ctrlDao;
    
    @Autowired
    private MarketingActService marketingActService;

    @Override
    public Coupon checkCoupon(CouponCheckRequest req) throws AppException {
        Coupon coupon = null;

        if (req.getCouponId() != null) {
            //coupon = Coupon.findCoupon(req.getCouponId());
            coupon = couponDao.findUniqueByProperty("couponId", req.getCouponId());
        } else if (req.getSerialNo() != null) {
            //coupon = Coupon.findBySerialNo(req.getSerialNo());
            coupon = couponDao.findUniqueByProperty("serialNo", req.getSerialNo());
        }
        if (coupon == null) {
            throw new AppException(ErrorsCode.ERR_COUPON_NOT_EXIST,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] not exist.");
        }

        // 检查礼券状态是否无效
        if (coupon.getCouponStatus().equals(Coupon.STATUS_VALID) == false) {
            throw new AppException(ErrorsCode.ERR_COUPON_INVALID,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] expired.");
        }
        if (coupon.getMarketingAct().getEndDate().getTime() < new Date().getTime()
                || coupon.getMarketingAct().getStartDate().getTime() > new Date().getTime()) {
            throw new AppException(ErrorsCode.ERR_COUPON_EXPIRED,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] expired.");
        }

        // 检查活动状态过期或关闭
        if (coupon.getMarketingAct().getActStatus().equals(MarketingAct.STATUS_AFTER_GIVE) == false) {
            throw new AppException(ErrorsCode.ERR_COUPON_INVALID,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "] invalid.");
        }

        boolean hasPartner = false;
        for (Iterator<Partner> it = coupon.getMarketingAct().getPartners().iterator(); it.hasNext();) {
            Partner partner = it.next();
            if (partner.getPartnerNo().equals(req.getPartnerNo())) {
                hasPartner = true;
                break;
            }
        }
        if (hasPartner == false) {
            throw new AppException(ErrorsCode.ERR_COUPON_PARTNER_NOT_FOUND,
                    "Request id=[" + req.getCouponId() + "],serialNo=["
                    + req.getSerialNo() + "],partner=["
                    + req.getPartnerNo() + "] partner not found.");
        }

        return coupon;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Coupon consumeCoupon(CouponCheckRequest req) throws AppException {
        checkCoupon(req);


        Coupon coupon = null;
        if (req.getCouponId() != null) {
            //coupon = Coupon.findCoupon(req.getCouponId());
            coupon = couponDao.findUniqueByProperty("couponId", req.getCouponId());
        } else if (req.getSerialNo() != null) {
            //coupon = Coupon.findBySerialNo(req.getSerialNo());
            coupon = couponDao.findUniqueByProperty("serialNo", req.getSerialNo());
        }
        CoupontCtrl ctrl = new CoupontCtrl();
        ctrl.setPartnerNo(req.getPartnerNo());
        ctrl.setCouponId(coupon.getCouponId());
        ctrl.setSerialNo(coupon.getSerialNo());
        ctrl.setTraceNo(req.getTraceNo());
        ctrl.setDeviceNo(req.getDeviceNo());
        ctrl.setCheckDate(new Date());
        ctrl.setCheckDay(new Date());
        ctrl.setRebateRate(req.getRebateRate());
        ctrl.setBusinessType(req.getBizType());
        ctrl.setEncodeVersion("2");
        ctrl.setAmount(req.getAmount());
        ctrl.setBatchNo(req.getBatchNo());
        //ctrl.persist();
        ctrlDao.save(ctrl);

        coupon.setRemainTimes(coupon.getRemainTimes() - 1);
        if (req.getBizType().equals(Coupon.BIZ_TYPE_DISCOUNT)) {
            //
        }
        if (coupon.getRemainTimes() == 0) {
            coupon.setCouponStatus(Coupon.STATUS_FINISH);
        }
        //coupon.merge();
        couponDao.update(coupon);
        
        return coupon;

    }

    private List<CouponSummaryDayItem> summaryGroupByDay(
            List<CouponSummaryItem> summary) {
        Map<Date, CouponSummaryDayItem> temp = new HashMap<Date, CouponSummaryDayItem>();

        for (Iterator<CouponSummaryItem> it = summary.iterator(); it.hasNext();) {
            CouponSummaryItem cs = it.next();
            CouponSummaryDayItem day = temp.get(cs.getDay());
            if (day == null) {
                day = new CouponSummaryDayItem();
                day.setDay(cs.getDay());
                day.setCount(cs.getCount());
                day.setExchangeAmount(cs.getExchangeAmount());
                day.setRebateAmount(cs.getRebateAmount());
                temp.put(cs.getDay(), day);
            } else {
                day.setCount(day.getCount() + cs.getCount());
                day.setExchangeAmount(day.getExchangeAmount().add(
                        cs.getExchangeAmount()));
                day.setRebateAmount(day.getRebateAmount().add(
                        cs.getRebateAmount()));
            }
        }
        List<CouponSummaryDayItem> ret = new ArrayList<CouponSummaryDayItem>();
        ret.addAll(temp.values());
        return ret;
    }

    private List<CouponSummaryPartnerItem> summaryGroupByPartner(
            List<CouponSummaryItem> summary) {
        Map<String, CouponSummaryPartnerItem> temp = new HashMap<String, CouponSummaryPartnerItem>();
        for (Iterator<CouponSummaryItem> it = summary.iterator(); it.hasNext();) {
            CouponSummaryItem cs = it.next();
            CouponSumaryReport.CouponSummaryPartnerItem partner = temp.get(cs.getDay());
            if (partner == null) {
                partner = new CouponSumaryReport.CouponSummaryPartnerItem();
                partner.setPartnerName(cs.getPartnerName());
                partner.setCount(cs.getCount());
                partner.setExchangeAmount(cs.getExchangeAmount());
                partner.setRebateAmount(cs.getRebateAmount());
                temp.put(cs.getPartnerName(), partner);
            } else {
                partner.setCount(cs.getCount());
                partner.setExchangeAmount(partner.getExchangeAmount().add(
                        cs.getExchangeAmount()));
                partner.setRebateAmount(partner.getRebateAmount().add(
                        cs.getRebateAmount()));
            }
        }
        List<CouponSummaryPartnerItem> ret = new ArrayList<CouponSummaryPartnerItem>();
        ret.addAll(temp.values());
        return ret;
    }

    @Override
    public CouponSumaryReport summaryByMarketing(Long actNo) {
        //List<CouponSummaryItem> summary = Coupon.summaryPartnerByAct(actNo);
        
        List<CouponSummaryItem> summary = couponDao.summaryPartnerByAct(actNo);
        
        CouponSumaryReport report = new CouponSumaryReport();
        report.setSummary(summary);
        report.setSummaryDays(summaryGroupByDay(summary));
        report.setSummaryPartners(summaryGroupByPartner(summary));
        return report;
    }

    @Override
    @Transactional
    public Coupon reversalCoupon(Long couponId, String mobile) {
        //Coupon coupon = Coupon.findCoupon(couponId);
        Coupon coupon = couponDao.findUniqueByProperty("couponId", couponId);
        coupon.setCouponStatus(Coupon.STATUS_LOST);
       //coupon.merge();
        
        couponDao.update(coupon);
        
        coupon = giveCoupons(coupon, mobile, coupon.getMarketingAct());
        this.marketingActService.genCode(coupon, coupon.getMarketingAct());
        return coupon;
    }

    /**
     * 生成指定用户礼券
     * 
     * @param cp
     * @param mobile
     * @param act
     * @return
     */
    private Coupon giveCoupons(Coupon cp, String mobile, MarketingAct act) {
        Coupon coupon = new Coupon();
        coupon.setAcctNo(cp.getAcctNo());
        if (mobile == null) {
            coupon.setAcctMobile(cp.getAcctMobile());
        } else {
            coupon.setAcctMobile(mobile);
        }
        coupon.setCheckCode(cp.getCheckCode());
        coupon.setMarketingAct(act);
        coupon.setCouponStatus(Coupon.STATUS_VALID);
        coupon.setSerialNo(UuidHelper.generateUUID());
        coupon.setBusinessType(act.getBizNo());
        coupon.setRebateRate(act.getRebateRate());
        coupon.setTimes(act.getTimes());
        coupon.setRemainTimes(cp.getRemainTimes());
        coupon.setGenTime(new Date());
        //coupon.persist();
        couponDao.save(coupon);
        return coupon;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void backOff(CouponBackoffRequest req) {
        //Coupon coupon = Coupon.findBySerialNo(req.getSerialNo());
        Coupon coupon  = couponDao.findBySerialNo(req.getSerialNo());
        //List<CoupontCtrl> list = CoupontCtrl.findBySerialNo(req.getSerialNo());
        List<CoupontCtrl> list = ctrlDao.findBySerialNo(req.getSerialNo());
        
        CoupontCtrl ctrl = null;
        for (CoupontCtrl coupontCtrl : list) {
            if (coupontCtrl.getBatchNo().equals(req.getBatchNo())
                    && coupontCtrl.getDeviceNo().equals(req.getDeviceNo())
                    && coupontCtrl.getPartnerNo().equals(req.getPartnerNo())
                    && coupontCtrl.getTraceNo().equals(req.getTraceNo())
                    && coupontCtrl.getVoidFlag().equals(coupontCtrl.VOID_FLAG_NORMAL)) {
                ctrl = coupontCtrl;
            }
        }
        if (coupon != null && ctrl != null) {
            ctrl.setVoidFlag(CoupontCtrl.VOID_FLAG_BACKOFF);
            //ctrl.merge();

            ctrlDao.update(ctrl);
            coupon.setRemainTimes(coupon.getRemainTimes() + 1);
            //coupon.merge();
            
            couponDao.update(coupon);
        }

    }

    @Override
    public Coupon queryCoupon(CouponCheckRequest req) throws AppException {
        return this.checkCoupon(req);

    }

	@Override
	public Coupon findByCoupon(Long couponId) {
		List<Coupon> list=this.couponDao.findByProperty("couponId", couponId);
		return list.size()>0?list.get(0):null;
	}

	public List<Coupon> findByCondition(SimpleQueryCondition condition,Integer start,Integer end){
		return this.couponDao.excuteSimpleQuery(condition, start, end);
	}

	@Override
	public ReportResult reportCount(ReportForm reportForm) {
		return this.couponDao.reportCount(reportForm);
	}

	@Override
	public ReportResult reportDetail(ReportForm reportForm) {
		return this.couponDao.reportDetail(reportForm);
	}

	@Override
	public int countByCondition(SimpleQueryCondition condition) {
		return this.couponDao.countSimpleQuery(condition);
	}
}
