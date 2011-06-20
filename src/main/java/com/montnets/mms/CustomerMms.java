/**
 * CustomerMms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.montnets.mms;

public interface CustomerMms extends javax.xml.rpc.Service {
    public java.lang.String getCustomerMmsSoapAddress();

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap() throws javax.xml.rpc.ServiceException;

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getCustomerMmsSoap12Address();

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap12() throws javax.xml.rpc.ServiceException;

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
