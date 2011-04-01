package com.newland.beecode.domain;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Oper.class)
public class OperIntegrationTest {

    @Test
    public void testMarkerMethod() {
    	Oper oper = new Oper();
    	oper.setGenTime(new java.util.Date());
    	oper.setOperName("roo");
    	oper.setVersion(0);
    	oper.persist();
    }
}
