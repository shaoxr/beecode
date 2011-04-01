package com.newland.posp;


public interface ResponseListener {
	void responseReceived (Message resp, Object handBack);
    void expired (Object handBack);
}
