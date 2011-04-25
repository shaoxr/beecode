package com.newland.posp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {
	private final Logger logger = LoggerFactory.getLogger(Request.class);
	private Message request, response;
	private long requestTime, txTime, responseTime;
	private boolean expired;

	public Request(Message request) {
		this.request = request;
		requestTime = System.currentTimeMillis();
		txTime = 0;
		expired = false;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Message getRequest() {
		return this.request;
	}

	public void setTransmitted() {
		txTime = System.currentTimeMillis();
	}

	public boolean isTransmitted() {
		return txTime != 0;
	}

	public Message getResponse(long timeout) {
		synchronized (this) {
			if (response == null) {
				try {
					if (timeout > 0)
						wait(timeout);
					else
						wait();
				} catch (InterruptedException e) {
				}
			}
			setExpired(response == null);
		}
		logger.debug("return response");
		return response;
	}

	public void setResponse(Message m) {
		responseTime = System.currentTimeMillis();
		synchronized (this) {
			response = m;
			logger.debug("got response!...");
			this.notify();
		}
	}

	public long getResponseTime() {
		return (responseTime - requestTime);
	}
}
