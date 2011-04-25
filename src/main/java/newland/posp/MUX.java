package com.newland.posp;

import java.io.IOException;

import com.newland.message.MessageException;


public interface MUX {
	/**
	 * @para3m m
	 *            message to send
	 * @param timeout
	 *            time to wait for a message
	 * @return received message or null
	 * @throws ISOException
	 */
	public Message request(Message m, long timeout) throws IOException, MessageException;

	public void request(Message m, long timeout, ResponseListener r,
			Object handBack) throws IOException,MessageException;

	/**
	 * @return true if connected
	 */
	public boolean isConnected();

}
