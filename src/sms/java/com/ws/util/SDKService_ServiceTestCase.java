/**
 * SDKService_ServiceTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ws.util;

public class SDKService_ServiceTestCase extends junit.framework.TestCase {
    public SDKService_ServiceTestCase(java.lang.String name) {
        super(name);
    }

    public void testSDKServiceWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.ws.util.SDKService_ServiceLocator().getSDKServiceAddress() + "?WSDL");
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.ws.util.SDKService_ServiceLocator().getServiceName());
        assertTrue(service != null);
    }

    public void test1SDKServiceGetVersion() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        java.lang.String value = null;
        value = binding.getVersion();
        // TBD - validate results
    }

    public void test2SDKServiceGetReport() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.ws.util.StatusReport[] value = null;
        value = binding.getReport(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test3SDKServiceCancelMOForward() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.cancelMOForward(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test4SDKServiceChargeUp() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.chargeUp(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test5SDKServiceGetBalance() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        double value = -3;
        value = binding.getBalance(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test6SDKServiceGetEachFee() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        double value = -3;
        value = binding.getEachFee(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test7SDKServiceGetMO() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        com.ws.util.Mo[] value = null;
        value = binding.getMO(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test8SDKServiceLogout() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.logout(new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test9SDKServiceRegistDetailInfo() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.registDetailInfo(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test10SDKServiceRegistEx() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.registEx(new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test11SDKServiceSendSMS() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.sendSMS(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String[0], new java.lang.String(), new java.lang.String(), new java.lang.String(), 0, 0);
        // TBD - validate results
    }

    public void test12SDKServiceSerialPwdUpd() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.serialPwdUpd(new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test13SDKServiceSetMOForward() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.setMOForward(new java.lang.String(), new java.lang.String(), new java.lang.String());
        // TBD - validate results
    }

    public void test14SDKServiceSetMOForwardEx() throws Exception {
        com.ws.util.SDKServiceBindingStub binding;
        try {
            binding = (com.ws.util.SDKServiceBindingStub)
                          new com.ws.util.SDKService_ServiceLocator().getSDKService();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        int value = -3;
        value = binding.setMOForwardEx(new java.lang.String(), new java.lang.String(), new java.lang.String[0]);
        // TBD - validate results
    }
    public static void main(String[] args) throws Exception{
      	 com.ws.util.SDKServiceBindingStub binding;
           try {
               binding = (com.ws.util.SDKServiceBindingStub)
                             new com.ws.util.SDKService_ServiceLocator().getSDKService();
           }
           catch (javax.xml.rpc.ServiceException jre) {
               if(jre.getLinkedCause()!=null)
                   jre.getLinkedCause().printStackTrace();
               throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
           }
           assertNotNull("binding is null", binding);

           	int value = -3;
           	//注册序列号
//            value = binding.registEx("2SDK-EMY-6688-JBVTN", "123456", "225480");
//            System.out.println(value);
           	
               //获取余额
            double d=binding.getBalance("2SDK-EMY-6688-JBVTN", "225480");
   	        System.out.println(d);
   	        
   	        //发送短信
   	        int p=binding.sendSMS("2SDK-EMY-6688-JBVTN", "123456", null, new String[]{"15806045105"}, "测试短信", "", "gbk",5, 888888);
   	        System.out.println(p);
   	        
   	        //获取状态报告
		//用xFire方式调用将获取List
   	        StatusReport[] statusReport=(StatusReport[])binding.getReport("6SDK-EMY-6688-JBRMW", "375703");
   			  if(statusReport!=null){
   				System.out.println("状态报告数量="+statusReport.length);
   			}
   			for(int i=0;statusReport!=null && i<statusReport.length;i++){
   				StatusReport report=(StatusReport)statusReport[i];
   				String id=report.getSeqID()+"";
   				System.out.println("Status="+report.getReportStatus()+"\tPhone="+report.getMobile()+"\tSEQid="+report.getSeqID()+"\tMemo="+report.getMemo()+"\tServiceCodeAdd="+report.getServiceCodeAdd()+"\tSubmitDate="+report.getSubmitDate()+"\tReceiveDate="+report.getReceiveDate()+"\tErrorCode="+report.getErrorCode());
   			}
   	        
       
   	}
   }
