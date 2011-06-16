/**
 * CustomerMmsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.montnets.mms;

public class CustomerMmsLocator extends org.apache.axis.client.Service implements com.montnets.mms.CustomerMms {

    public CustomerMmsLocator() {
    }


    public CustomerMmsLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CustomerMmsLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CustomerMmsSoap
    private java.lang.String CustomerMmsSoap_address = "http://si.montnets.com:9016/CustomerMms.asmx";

    public java.lang.String getCustomerMmsSoapAddress() {
        return CustomerMmsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CustomerMmsSoapWSDDServiceName = "CustomerMmsSoap";

    public java.lang.String getCustomerMmsSoapWSDDServiceName() {
        return CustomerMmsSoapWSDDServiceName;
    }

    public void setCustomerMmsSoapWSDDServiceName(java.lang.String name) {
        CustomerMmsSoapWSDDServiceName = name;
    }

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CustomerMmsSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCustomerMmsSoap(endpoint);
    }

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.montnets.mms.CustomerMmsSoap_BindingStub _stub = new com.montnets.mms.CustomerMmsSoap_BindingStub(portAddress, this);
            _stub.setPortName(getCustomerMmsSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCustomerMmsSoapEndpointAddress(java.lang.String address) {
        CustomerMmsSoap_address = address;
    }


    // Use to get a proxy class for CustomerMmsSoap12
    private java.lang.String CustomerMmsSoap12_address = "http://si.montnets.com:9016/CustomerMms.asmx";

    public java.lang.String getCustomerMmsSoap12Address() {
        return CustomerMmsSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CustomerMmsSoap12WSDDServiceName = "CustomerMmsSoap12";

    public java.lang.String getCustomerMmsSoap12WSDDServiceName() {
        return CustomerMmsSoap12WSDDServiceName;
    }

    public void setCustomerMmsSoap12WSDDServiceName(java.lang.String name) {
        CustomerMmsSoap12WSDDServiceName = name;
    }

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CustomerMmsSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCustomerMmsSoap12(endpoint);
    }

    public com.montnets.mms.CustomerMmsSoap_PortType getCustomerMmsSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.montnets.mms.CustomerMmsSoap12Stub _stub = new com.montnets.mms.CustomerMmsSoap12Stub(portAddress, this);
            _stub.setPortName(getCustomerMmsSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCustomerMmsSoap12EndpointAddress(java.lang.String address) {
        CustomerMmsSoap12_address = address;
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
            if (com.montnets.mms.CustomerMmsSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.montnets.mms.CustomerMmsSoap_BindingStub _stub = new com.montnets.mms.CustomerMmsSoap_BindingStub(new java.net.URL(CustomerMmsSoap_address), this);
                _stub.setPortName(getCustomerMmsSoapWSDDServiceName());
                return _stub;
            }
            if (com.montnets.mms.CustomerMmsSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.montnets.mms.CustomerMmsSoap12Stub _stub = new com.montnets.mms.CustomerMmsSoap12Stub(new java.net.URL(CustomerMmsSoap12_address), this);
                _stub.setPortName(getCustomerMmsSoap12WSDDServiceName());
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
        if ("CustomerMmsSoap".equals(inputPortName)) {
            return getCustomerMmsSoap();
        }
        else if ("CustomerMmsSoap12".equals(inputPortName)) {
            return getCustomerMmsSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://montnets.com/", "CustomerMms");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://montnets.com/", "CustomerMmsSoap"));
            ports.add(new javax.xml.namespace.QName("http://montnets.com/", "CustomerMmsSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CustomerMmsSoap".equals(portName)) {
            setCustomerMmsSoapEndpointAddress(address);
        }
        else 
if ("CustomerMmsSoap12".equals(portName)) {
            setCustomerMmsSoap12EndpointAddress(address);
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
