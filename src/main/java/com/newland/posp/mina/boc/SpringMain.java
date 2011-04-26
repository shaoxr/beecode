package com.newland.posp.mina.boc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	
	public static void main(String[] args) throws Exception {
        if (System.getProperty("com.sun.management.jmxremote") != null) {
            new ClassPathXmlApplicationContext(getJmxApplicationContexts());
            System.out.println("JMX enabled.");
        } else {
            new ClassPathXmlApplicationContext(getApplicationContext());
            System.out
                    .println("JMX disabled. Please set the "
                            + "'com.sun.management.jmxremote' system property to enable JMX.");
        }
        System.out.println("Listening ...");
    }

    public static String getApplicationContext() {
        return "com/newland/posp/mina/boc/serverContext.xml";
    }

    public static String[] getJmxApplicationContexts() {
        return new String[] { "com/newland/posp/mina/boc/serverContext.xml",
                "com/newland/posp/mina/boc/jmxContext.xml" };
    }
}
