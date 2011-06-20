/**
 * Wmgw.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.montnets.sms;

public interface Wmgw extends javax.xml.rpc.Service {
    public java.lang.String getwmgwSoap12Address();

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap12() throws javax.xml.rpc.ServiceException;

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getwmgwSoapAddress();

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap() throws javax.xml.rpc.ServiceException;

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
