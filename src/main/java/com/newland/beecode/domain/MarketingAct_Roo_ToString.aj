// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.newland.beecode.domain;

import java.lang.String;

privileged aspect MarketingAct_Roo_ToString {
    
    public String MarketingAct.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BizName: ").append(getBizName()).append(", ");
        sb.append("ActStatusDesc: ").append(getActStatusDesc()).append(", ");
        sb.append("Summary: ").append(getSummary()).append(", ");
        sb.append("ActNo: ").append(getActNo()).append(", ");
        sb.append("ActName: ").append(getActName()).append(", ");
        sb.append("StartDate: ").append(getStartDate()).append(", ");
        sb.append("EndDate: ").append(getEndDate()).append(", ");
        sb.append("ActDetail: ").append(getActDetail()).append(", ");
        sb.append("GenTime: ").append(getGenTime()).append(", ");
        sb.append("SendTime: ").append(getSendTime()).append(", ");
        sb.append("Times: ").append(getTimes()).append(", ");
        sb.append("CheckCode: ").append(getCheckCode()).append(", ");
        sb.append("RebateRate: ").append(getRebateRate()).append(", ");
        sb.append("Amount: ").append(getAmount()).append(", ");
        sb.append("ActStatus: ").append(getActStatus()).append(", ");
        sb.append("CouponSum: ").append(getCouponSum()).append(", ");
        sb.append("MmsSendSum: ").append(getMmsSendSum()).append(", ");
        sb.append("SmsSendSum: ").append(getSmsSendSum()).append(", ");
        sb.append("SendErrSum: ").append(getSendErrSum()).append(", ");
        sb.append("BizNo: ").append(getBizNo()).append(", ");
        sb.append("Partners: ").append(getPartners() == null ? "null" : getPartners().size()).append(", ");
        sb.append("MarketingCatalog: ").append(getMarketingCatalog()).append(", ");
        sb.append("OperNo: ").append(getOperNo()).append(", ");
        sb.append("MmsContent: ").append(getMmsContent()).append(", ");
        sb.append("MmsTitle: ").append(getMmsTitle()).append(", ");
        sb.append("BindCard: ").append(getBindCard()).append(", ");
        sb.append("FileName: ").append(getFileName()).append(", ");
        sb.append("MmsTemplate: ").append(getMmsTemplate());
        return sb.toString();
    }
    
}