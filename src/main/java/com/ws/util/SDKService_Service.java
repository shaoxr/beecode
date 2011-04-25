/**
 * SDKService_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.util;

public interface SDKService_Service extends javax.xml.rpc.Service {
    public java.lang.String getSDKServiceAddress();

    public com.ws.util.SDKClient getSDKService() throws javax.xml.rpc.ServiceException;

    public com.ws.util.SDKClient getSDKService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
