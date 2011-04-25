package com.newland.posp.channel.mina;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newland.posp.MUX;
import com.newland.posp.Message;
import com.newland.posp.boc.BocIstHeader;
import com.newland.posp.boc.BocIstPosMessage;

public class TestSpringChannel {
	ApplicationContext context = null;
	public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(getApplicationContext());
        System.out.println("Listening ...");
        BocIstPosMessage  msg = new BocIstPosMessage();  
        BocIstHeader header = new BocIstHeader("0010", "1100");
        msg.setHeader(header);
        msg.setString(0, "10");
        msg.setString(39, "AA");
        msg.setString(11, "100000");
        msg.setString(41, "12345678");
        msg.setString(12, "100000");
        msg.setString(14, "T1001234");
        //Thread.sleep(100);
        MUX channel = (MUX)context.getBean("istChannel");
        Thread.sleep(500);
        Message msg2 = channel.request(msg, 10000);
        System.out.println("Field 0:"+msg2.getString(0));
        System.out.println("Field 39:"+msg2.getString(39));
    }

    public static String getApplicationContext() {
        return "com/newland/posp/channel/mina/ChannelContext.xml";
    }
    
    public TestSpringChannel(ApplicationContext context) {
    	this.context = context;
    }
}
