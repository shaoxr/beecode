/**
 * CustomerMmsSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.montnets.mms;

public interface CustomerMmsSoap_PortType extends java.rmi.Remote {
    public java.lang.String sendMms(java.lang.String userId, java.lang.String password, java.lang.String mobiles, java.lang.String base64Content, java.lang.String subject) throws java.rmi.RemoteException;
    public java.lang.String sendTemplateMms(java.lang.String userId, java.lang.String password, java.lang.String base64Content, java.lang.String subject, java.lang.String base64TemplateData, int frameNumber, int columnCount, int rowCount) throws java.rmi.RemoteException;
    public java.lang.String getMmsBalance(java.lang.String userId, java.lang.String password) throws java.rmi.RemoteException;
}
