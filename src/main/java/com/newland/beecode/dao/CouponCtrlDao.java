/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.CoupontCtrl;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author skylai
 */
@Repository("couponCtlDao")
public class CouponCtrlDao extends SimpleHibernateTemplate<CoupontCtrl, Long> {

    public List<CoupontCtrl> findBySerialNo(String serialNo) {

        return this.find("select couponCtrl from CoupontCtrl as couponCtrl where couponCtrl.serialNo=?", serialNo);
    }

    public List<CoupontCtrl> findCoupontCtrlEntries(int firstResult, int maxResults) {

        return findListByQuery("select o from CoupontCtrl o", firstResult, maxResults);
    }

    public long countCoupontCtrls() {
        return this.findLong("select count(o) from CoupontCtrl o");
    }
}
