package com.newland.posp.mina.boctchannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.util.SessionLog;

import com.newland.posp.BaseHeader;
import com.newland.posp.Message;
import com.newland.posp.MessageKeyGenerator;
import com.newland.posp.boc.BocDepotHeader;
import com.newland.posp.boc.BocDepotPosMessage;
import com.newland.posp.boc.BocIstHeader;
import com.newland.posp.boc.BocIstPosMessage;
import com.newland.posp.mina.boc.DepotPosProtocolHandler;

/**
 * 要求IST跟POS均从主机6666端口转发进行
 * @author lance
 * POSP_NEW
 * 2009-1-19
 */
public class TwicePosProtocalHandler  extends IoHandlerAdapter {

	private final Log logger = LogFactory.getLog(DepotPosProtocolHandler.class);
	private Map<String,IoSession> sessions = Collections.synchronizedMap(new Hashtable<String ,IoSession>());

	// TODO 异常情况不应该这么处理
	public void exceptionCaught(IoSession session, Throwable cause) {
        SessionLog.error(session, "", cause);
        // Close connection when unexpected exception is caught.
        session.close();
    }
	
	 public void sessionClosed(IoSession session) throws Exception {
		 sessions.remove(session.getRemoteAddress().toString());
	 }
	 
	 public int getNumberOfSessions() {
		 return sessions.size();
	 }
	 
	 public void messageReceived(IoSession session, Object message) {
		 if(message instanceof BocDepotPosMessage){
			 BocDepotPosMessage msg = (BocDepotPosMessage)message;
			 process(session,msg);
		 }else if(message instanceof BocIstPosMessage){
			 BocIstPosMessage msg = (BocIstPosMessage)message;
			 process(session,msg);
		 }


	 }
	 
	/** 
	 * 需要进行交易转发的交易列表
	 */
	private static List<Integer> needToIso = new ArrayList<Integer>();
	
	private final Map<String, Message> muxRxQueue = new ConcurrentHashMap<String, Message>();
	private MessageKeyGenerator keyGenerator = null;
	
	/**
	 * 转发回来需要转发POSP的报文
	 */
	private static List<Integer> isoResponseNotNeedToPosp = new ArrayList<Integer>();
	

	private static final int _TLV_TRANS_TYPE_KEY = 0;
	private static final int _TLV_TRANS_RETURN_KEY = 39;
	private static final int _IST_TRANS_RETURN_KEY = 39;
	
	static{
		needToIso.add(13);
		needToIso.add(15);
		needToIso.add(18);
		
		isoResponseNotNeedToPosp.add(15);
	}
	private void process(IoSession session,BocDepotPosMessage msg) {
		String strTransferType = (String)msg.getField(_TLV_TRANS_TYPE_KEY).getValue();
		int transferType = Integer.parseInt(strTransferType);
		
		if(needToIso.contains(transferType)){

			BocDepotHeader msgHeader = msg.getHeader();
			
			BocIstPosMessage bipm = new BocIstPosMessage();
			byte[]isoRawData = msg.getIsoRawData();
			byte [] sendIsoData = new byte[isoRawData.length];
			System.arraycopy(isoRawData, 0, sendIsoData, 0, isoRawData.length);
			bipm.setIsoRawData(sendIsoData);
			BocIstHeader header = new BocIstHeader();
			header.setAppHeader(msgHeader.getAppHeader());
			
			//把头地址改成IST的地址，并转发
			header.setSource(BaseHeader._TPDU_BOC_POSP);
			header.setDestination(BaseHeader._TPDU_BOC_IST);
			
			bipm.setHeader(header);
			/**
			 * 保存现有状态
			 */
			String key = keyGenerator.getKey(msg);
			logger.error("general key:"+key);
			muxRxQueue.put(key, bipm);
			session.write(bipm);
			return;
		}else{
			//doservice();
			msg.getHeader().swapDirection();
			session.write(msg);
		}
	}
	private void process(IoSession session,BocIstPosMessage msg) {
		String key = keyGenerator.getKey(msg);
		if(muxRxQueue.containsKey(key)){
			BocDepotPosMessage bdpmsg  = (BocDepotPosMessage)muxRxQueue.get(key);
			muxRxQueue.remove(key);
			String strTransferType = (String)bdpmsg.getField(_TLV_TRANS_TYPE_KEY).getValue();
			int transferType = Integer.parseInt(strTransferType);
			
			if(!isoResponseNotNeedToPosp.contains(transferType)){
				//转发service;
			}
			
			byte[]isoRawData = msg.getIsoRawData();
			byte [] sendIsoData = new byte[isoRawData.length];
			System.arraycopy(isoRawData, 0, sendIsoData, 0, isoRawData.length);
			bdpmsg.setIsoRawData(isoRawData);
			bdpmsg.getHeader().swapDirection();
			
			session.write(bdpmsg);
			return;
		}else{
			logger.error("the key ["+key+"] have not found its message!");
			return;
		}

	}

	public void setKeyGenerator(MessageKeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}




}
