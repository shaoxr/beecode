package com.newland.posp;

import java.io.IOException;

import com.newland.message.MessageException;


public interface Channel extends Responder {
	/**
	 * @return true if source is connected and usable
	 */
	public boolean isConnected();
	
	/**
	 * 发送响应信息
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws MessageException
	 */
	public void send(Message data) throws IOException, MessageException;

	/**
	 * Receives an ISOMsg
	 * 
	 * @return the Message received
	 * @exception IOException
	 * @exception ISOException
	 */
	public Message receive() throws IOException, MessageException;

	public Message receive(long timeout) throws IOException, MessageException;
}
