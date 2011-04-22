// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import com.newland.beecode.domain.CoupontCtrl;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect CoupontCtrlDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CoupontCtrlDataOnDemand: @Component;
    
    private Random CoupontCtrlDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<CoupontCtrl> CoupontCtrlDataOnDemand.data;
    
    public CoupontCtrl CoupontCtrlDataOnDemand.getNewTransientCoupontCtrl(int index) {
        com.newland.beecode.domain.CoupontCtrl obj = new com.newland.beecode.domain.CoupontCtrl();
        obj.setCouponId(new Integer(index).longValue());
        java.lang.String serialNo = "serialNo_" + index;
        if (serialNo.length() > 45) {
            serialNo  = serialNo.substring(0, 45);
        }
        obj.setSerialNo(serialNo);
        obj.setTraceNo("traceNo_" + index);
        obj.setBatchNo("batchNo_" + index);
        java.lang.String encodeVersion = "_" + index;
        if (encodeVersion.length() > 2) {
            encodeVersion  = encodeVersion.substring(0, 2);
        }
        obj.setEncodeVersion(encodeVersion);
        java.lang.String deviceNo = "deviceNo_" + index;
        if (deviceNo.length() > 16) {
            deviceNo  = deviceNo.substring(0, 16);
        }
        obj.setDeviceNo(deviceNo);
        java.lang.String partnerNo = "partnerNo_" + index;
        if (partnerNo.length() > 32) {
            partnerNo  = partnerNo.substring(0, 32);
        }
        obj.setPartnerNo(partnerNo);
        obj.setCheckDay(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setCheckDate(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setAmount(new java.math.BigDecimal(index));
        obj.setRebateRate(new java.math.BigDecimal(index));
        java.lang.String businessType = "_" + index;
        if (businessType.length() > 2) {
            businessType  = businessType.substring(0, 2);
        }
        obj.setBusinessType(businessType);
        obj.setVoidFlag("voidFlag_" + index);
        return obj;
    }
    
    public CoupontCtrl CoupontCtrlDataOnDemand.getSpecificCoupontCtrl(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        CoupontCtrl obj = data.get(index);
        return CoupontCtrl.findCoupontCtrl(obj.getId());
    }
    
    public CoupontCtrl CoupontCtrlDataOnDemand.getRandomCoupontCtrl() {
        init();
        CoupontCtrl obj = data.get(rnd.nextInt(data.size()));
        return CoupontCtrl.findCoupontCtrl(obj.getId());
    }
    
    public boolean CoupontCtrlDataOnDemand.modifyCoupontCtrl(CoupontCtrl obj) {
        return false;
    }
    
    public void CoupontCtrlDataOnDemand.init() {
        data = com.newland.beecode.domain.CoupontCtrl.findCoupontCtrlEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'CoupontCtrl' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.newland.beecode.domain.CoupontCtrl>();
        for (int i = 0; i < 10; i++) {
            com.newland.beecode.domain.CoupontCtrl obj = getNewTransientCoupontCtrl(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}