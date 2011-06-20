/**
 * WmgwSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.montnets.sms;

public interface WmgwSoap_PortType extends java.rmi.Remote {

    /**
     * 短信息发送接口
     */
    public java.lang.String mongateCsSpSendSmsNew(java.lang.String userId, java.lang.String password, java.lang.String pszMobis, java.lang.String pszMsg, int iMobiCount, java.lang.String pszSubPort) throws java.rmi.RemoteException;

    /**
     * 接收上行信息接口
     */
    public java.lang.String[] mongateCsGetSmsExEx(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 获取状态报告接口
     */
    public java.lang.String[] mongateCsGetStatusReportExEx(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 查询余额接口
     */
    public int mongateQueryBalance(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;
}
