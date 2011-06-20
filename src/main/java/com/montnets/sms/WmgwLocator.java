/**
 * WmgwLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.montnets.sms;

public class WmgwLocator extends org.apache.axis.client.Service implements com.montnets.sms.Wmgw {

    public WmgwLocator() {
    }


    public WmgwLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WmgwLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for wmgwSoap12
    private java.lang.String wmgwSoap12_address = "http://ws.montnets.com:9002/MWGate/wmgw.asmx";

    public java.lang.String getwmgwSoap12Address() {
        return wmgwSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wmgwSoap12WSDDServiceName = "wmgwSoap12";

    public java.lang.String getwmgwSoap12WSDDServiceName() {
        return wmgwSoap12WSDDServiceName;
    }

    public void setwmgwSoap12WSDDServiceName(java.lang.String name) {
        wmgwSoap12WSDDServiceName = name;
    }

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wmgwSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwmgwSoap12(endpoint);
    }

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.montnets.sms.WmgwSoap12Stub _stub = new com.montnets.sms.WmgwSoap12Stub(portAddress, this);
            _stub.setPortName(getwmgwSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwmgwSoap12EndpointAddress(java.lang.String address) {
        wmgwSoap12_address = address;
    }


    // Use to get a proxy class for wmgwSoap
    private java.lang.String wmgwSoap_address = "http://ws.montnets.com:9002/MWGate/wmgw.asmx";

    public java.lang.String getwmgwSoapAddress() {
        return wmgwSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String wmgwSoapWSDDServiceName = "wmgwSoap";

    public java.lang.String getwmgwSoapWSDDServiceName() {
        return wmgwSoapWSDDServiceName;
    }

    public void setwmgwSoapWSDDServiceName(java.lang.String name) {
        wmgwSoapWSDDServiceName = name;
    }

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(wmgwSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getwmgwSoap(endpoint);
    }

    public com.montnets.sms.WmgwSoap_PortType getwmgwSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.montnets.sms.WmgwSoap_BindingStub _stub = new com.montnets.sms.WmgwSoap_BindingStub(portAddress, this);
            _stub.setPortName(getwmgwSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setwmgwSoapEndpointAddress(java.lang.String address) {
        wmgwSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.montnets.sms.WmgwSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.montnets.sms.WmgwSoap12Stub _stub = new com.montnets.sms.WmgwSoap12Stub(new java.net.URL(wmgwSoap12_address), this);
                _stub.setPortName(getwmgwSoap12WSDDServiceName());
                return _stub;
            }
            if (com.montnets.sms.WmgwSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.montnets.sms.WmgwSoap_BindingStub _stub = new com.montnets.sms.WmgwSoap_BindingStub(new java.net.URL(wmgwSoap_address), this);
                _stub.setPortName(getwmgwSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("wmgwSoap12".equals(inputPortName)) {
            return getwmgwSoap12();
        }
        else if ("wmgwSoap".equals(inputPortName)) {
            return getwmgwSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "wmgw");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "wmgwSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "wmgwSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("wmgwSoap12".equals(portName)) {
            setwmgwSoap12EndpointAddress(address);
        }
        else 
if ("wmgwSoap".equals(portName)) {
            setwmgwSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
