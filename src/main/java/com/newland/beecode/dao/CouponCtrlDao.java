/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.intensoft.dao.hibernate.SimpleHibernateTemplate;
import com.newland.beecode.domain.CoupontCtrl;
import java.util.List;

/**
 *
 * @author skylai
 */
public class CouponCtrlDao extends SimpleHibernateTemplate<CoupontCtrl, Integer> {

    public List findBySerialNo(String serialNo) {

        return this.find("select couponCtrl from CoupontCtrl as couponCtrl where couponCtrl.serialNo=:serialNo", serialNo);
    }
}
