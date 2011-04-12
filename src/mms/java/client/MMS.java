/**
 * MMS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package client;

public interface MMS extends javax.xml.rpc.Service {
    public java.lang.String getMMSSoap12Address();

    public client.MMSSoap_PortType getMMSSoap12() throws javax.xml.rpc.ServiceException;

    public client.MMSSoap_PortType getMMSSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getMMSSoapAddress();

    public client.MMSSoap_PortType getMMSSoap() throws javax.xml.rpc.ServiceException;

    public client.MMSSoap_PortType getMMSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
