package com.newland.posp.channel.mina;

import java.io.IOException;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.newland.message.MessageException;
import com.newland.posp.ClientChannel;
import com.newland.posp.MUX;
import com.newland.posp.Message;
import com.newland.posp.MessageKeyGenerator;
import com.newland.posp.Request;
import com.newland.posp.RequestListener;
import com.newland.posp.ResponseListener;

public class MinaNacChannel implements ClientChannel, MUX, InitializingBean {
	private Logger logger = LoggerFactory.getLogger("MinaClientChannel");
	
	private SocketConnectorConfig socketConnectorConfig;
	private int connectTimeout = 30;
	private int timeout = 30;
	private int queueCapacity = 100;
	private SocketAddress socketAddress;
	private boolean autoConnect = true;
	private transient IoSession session;
	private final BlockingQueue<Message> receiveQueue;
	private final Map<String, Request> muxRxQueue;

	// For MUX implements
	private MessageKeyGenerator keyGenerator = null;
	private RequestListener requestListener = null;
	private int reconnectTime = 3;
	private int delay = 3000;

	public static final int CONNECT = 0;
	public static final int TX = 1;
	public static final int RX = 2;
	public static final int TX_EXPIRED = 3;
	public static final int RX_EXPIRED = 4;
	public static final int TX_PENDING = 5;
	public static final int RX_PENDING = 6;
	public static final int RX_UNKNOWN = 7;
	public static final int RX_FORWARDED = 8;
	public static final int SIZEOF_CNT = 9;

	private final int[] cnt;

	public MinaNacChannel() {
		receiveQueue = new LinkedBlockingQueue<Message>(queueCapacity);
		muxRxQueue = new ConcurrentHashMap<String, Request>();
		cnt = new int[SIZEOF_CNT];
	}

	public void setMessageKeyGenerator(MessageKeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public MessageKeyGenerator getMessageKeyGenerator() {
		return this.keyGenerator;
	}

	public void setRequestListener(RequestListener requestListener) {
		this.requestListener = requestListener;
	}

	public RequestListener getRequestListener() {
		return this.requestListener;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public int getQueueCapacity() {
		return this.queueCapacity;
	}

	public int getQueueSize() {
		return this.receiveQueue.size();
	}

	public void setAutoConnect(boolean autoConnect) {
		this.autoConnect = autoConnect;
	}

	public boolean getAutoConnect() {
		return this.autoConnect;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getConnectTimeout() {
		return this.connectTimeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public SocketAddress getSocketAddress() {
		return this.socketAddress;
	}

	public void setSocketConnectorConfig(
			SocketConnectorConfig socketConnectorConfig) {
		this.socketConnectorConfig = socketConnectorConfig;
	}

	public SocketConnectorConfig getSocketConnectorConfig() {
		return this.socketConnectorConfig;
	}

	public void connect() throws IOException {
		synchronized (this) {
			if (session != null && session.isConnected())
				return;
			SocketConnector connector = new SocketConnector();
			connector.connect(socketAddress, new ClientIoSessionHandler(),
					socketConnectorConfig);
			logger.debug("connect....");
		}
	}

	public void disconnect() throws IOException {
		if (session != null && session.isConnected()) {
			session.close();
		}
	}

	public void reconnect() throws IOException {
		disconnect();
		connect();
	}

	public boolean isConnected() {
		if (session != null) {
			return session.isConnected();
		} else
			return false;
	}

	public Message receive() throws IOException, MessageException {
		try {
			return receiveQueue.take();
		} catch (InterruptedException e) {
			throw new MessageException(e);
		}
	}

	/**
	 * 等待timeout秒，接收报文
	 */
	public Message receive(long timeout) throws IOException, MessageException {
		try {
			return receiveQueue.poll(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return null;
		}
	}

	/**
	 * 写失败会尝试连接1次
	 */
	public void send(Message data) throws IOException, MessageException {
		if (session != null && session.isConnected()) {
			session.write(data);
		} else {
			logger.info("will try to reconnect for 3 times!");
			for(int i=0;i<reconnectTime;i++){
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info("reconnect card for the "+i+" time!");
				connect();
				if (session != null && session.isConnected()) {
					session.write(data);
					return;
				}
			}
			throw new IOException("Cann't send message on a channel not connected");
		}
	}

	public String getRemoteIPAddress() {
		if (session != null) {
			SocketAddress address = session.getRemoteAddress();
			return address != null ? address.toString() : null;
		}
		return null;
	}

	public void afterPropertiesSet() throws Exception {
		if (autoConnect) {
			connect();
		}
	}

	/**
	 * Mux implements
	 */
	public Message request(Message m, long timeout) throws IOException,
			MessageException {
		
		if (keyGenerator == null) {
			throw new MessageException(
					"Mux channel must has a MessageKeyGenerator!");
		}
		String key = keyGenerator.getKey(m);
		if (muxRxQueue.containsKey(key)) {
			throw new MessageException("The message key '" + key
					+ "' is exist, please ensure message key unique!");
		}
		Request req = new Request(m);
		muxRxQueue.put(key, req);
		logger.debug("send key:"+key);
		send(m);
		return req.getResponse((int) timeout);
	}

	/**
	 * Mux implements
	 */
	public void request(Message m, long timeout, ResponseListener r,
			Object handBack) throws IOException, MessageException {
		throw new MessageException("Not implemented");
	}

	private void purgeMuxRxQueue() {
		synchronized (this) {
			Set<String> e = muxRxQueue.keySet();

			for (String key : e) {
				Request r = muxRxQueue.get(key);
				if (r != null && r.isExpired()) {
					muxRxQueue.remove(key);
					cnt[RX_EXPIRED]++;
				}
			}
		}
	}

	protected class ClientIoSessionHandler extends IoHandlerAdapter {
		
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		public ClientIoSessionHandler() {
		}

		public void exceptionCaught(IoSession session, Throwable cause)
				throws Exception {
			logger.error("Found exception on session", cause);
			reconnect();
		}
		
		 public void sessionClosed(IoSession session) throws Exception {
			logger.info("lose session from down line card:"+sdf.format(new Date()));
		 }
		public void messageReceived(IoSession session, Object message)
				throws Exception {
			boolean forward = true;
			cnt[RX]++;
			if (keyGenerator != null) {
				String key = keyGenerator.getKey((Message) message);
				logger.debug("recieve key:"+key);
				Request r = muxRxQueue.get(key);

				if (r != null) {
					muxRxQueue.remove(key);
					synchronized (r) {
						if (r.isExpired()) {
							logger.debug("received expired request's response!");
							if ((++cnt[RX_EXPIRED]) % 10 == 0)
								purgeMuxRxQueue();
						} else {
							logger.debug("Set request's response!");
							r.setResponse((Message) message);
							forward = false;
						}
					}
				}
			}

			if (forward) {
				if (requestListener != null) {
					// 根据Message内容转发,可以根据消息头选择合适的channel发送
					requestListener.process(MinaNacChannel.this,
							(Message) message);
					cnt[RX_FORWARDED]++;
				} else {
					// 未知消息
					cnt[RX_UNKNOWN]++;
					logger.warn("received unknown message!");
					receiveQueue.add((Message) message);
				}
			}
		}

		public void sessionCreated(IoSession session) throws Exception {
			session.setIdleTime(IdleStatus.BOTH_IDLE, 3000);
			MinaNacChannel.this.session = session;
			cnt[CONNECT]++;
		}

		public void messageSent(IoSession session, Object message)
				throws Exception {

			if (keyGenerator != null) {
				String key = keyGenerator.getKey((Message) message);

				if (muxRxQueue.containsKey(key)) {
					Request r = muxRxQueue.get(key);
					r.setTransmitted();
				}
			}
			cnt[TX]++;
		}
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setReconnectTime(int reconnectTime) {
		this.reconnectTime = reconnectTime;
	}
}
