/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.CouponCtrl;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("couponCtlDao")
public class CouponCtrlDao extends SimpleHibernateTemplate<CouponCtrl, Long> {

    public List<CouponCtrl> findBySerialNo(String serialNo) {

        return this.findByProperty("serialNo", serialNo);
    }

    public List<CouponCtrl> findCoupontCtrlEntries(int firstResult, int maxResults) {

        return findListByQuery("select o from CouponCtrl o", firstResult, maxResults);
    }

    public long countCoupontCtrls() {
        return this.findLong("select count(o) from CouponCtrl o");
    }
}
