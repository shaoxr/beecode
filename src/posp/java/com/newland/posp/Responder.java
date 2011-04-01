package com.newland.posp;

import java.io.IOException;

import com.newland.message.MessageException;


/**
 * 用于发送响应报文和关闭连接，在多线程环境，请求报文的接收进程和响应报文的发送进程
 * 不一样，发送响应报文通过这个接口发送响应报文.
 * 
 */
public interface Responder {
   
	/**
	 * 发送响应信息
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws MessageException
	 */
    public void send(Message data) throws IOException, MessageException;

    /**
     * @return true if source is connected and usable
     */
    public boolean isConnected();

    /**
     * 远端IP地址，如果没有返回null
     * @return
     */
    public String getRemoteIPAddress();
}
