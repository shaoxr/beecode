// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import java.lang.String;

privileged aspect CoupontCtrl_Roo_ToString {
    
    public String CoupontCtrl.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CouponId: ").append(getCouponId()).append(", ");
        sb.append("SerialNo: ").append(getSerialNo()).append(", ");
        sb.append("TraceNo: ").append(getTraceNo()).append(", ");
        sb.append("BatchNo: ").append(getBatchNo()).append(", ");
        sb.append("EncodeVersion: ").append(getEncodeVersion()).append(", ");
        sb.append("DeviceNo: ").append(getDeviceNo()).append(", ");
        sb.append("PartnerNo: ").append(getPartnerNo()).append(", ");
        sb.append("CheckDay: ").append(getCheckDay()).append(", ");
        sb.append("CheckDate: ").append(getCheckDate()).append(", ");
        sb.append("Amount: ").append(getAmount()).append(", ");
        sb.append("RebateRate: ").append(getRebateRate()).append(", ");
        sb.append("BusinessType: ").append(getBusinessType()).append(", ");
        sb.append("VoidFlag: ").append(getVoidFlag());
        return sb.toString();
    }
    
}