package com.newland.posp;

import java.io.IOException;
import java.net.SocketAddress;

public interface ClientChannel extends Channel {
	public void connect() throws IOException;

	public void disconnect() throws IOException;

	public void reconnect() throws IOException;

	public void setSocketAddress(SocketAddress socketAddress) throws IOException;
	
}
