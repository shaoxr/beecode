package com.newland.posp.mina.boc;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.util.SessionLog;

import com.newland.message.MessageException;
import com.newland.posp.BaseHeader;
import com.newland.posp.Message;
import com.newland.posp.boc.BocDepotHeader;
import com.newland.posp.boc.BocDepotPosMessage;
import com.newland.posp.boc.BocIstHeader;
import com.newland.posp.boc.BocIstPosMessage;
import com.newland.posp.channel.mina.MinaNacChannel;
import com.newland.posp.mina.requestlistener.ReturnPosCode;
import com.newland.tlv.TLVMappingUtils;
import com.newlandcomputer.posp.txn.InvokePosSerivice;

public class DepotPosProtocolHandler extends IoHandlerAdapter {
	
	private final Log logger = LogFactory.getLog(DepotPosProtocolHandler.class);
	private Map<String,IoSession> sessions = Collections.synchronizedMap(new Hashtable<String ,IoSession>());
	
	private InvokePosSerivice invokeService;
	
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
		 BocDepotPosMessage msg = (BocDepotPosMessage)message;
		 // do service
		 process(msg);

		 session.write(msg);
	 }
	 
	 
	 
    private MinaNacChannel isoNacChannel;
	/**
	 * 需要进行交易转发的交易列表
	 */
	private List<String> needToIso = new ArrayList<String>();
	/**
	 * 转发回来需要转发POSP的报文
	 */
	private List<String> isoResponseNotNeedToPosp = new ArrayList<String>();
	
	private static final int _TLV_TRANS_TYPE_KEY = 0;
	private static final int _TLV_TRANS_RETURN_KEY = 39;
	private static final int _IST_TRANS_RETURN_KEY = 39;
	
	private int timeout = 60000;
	
	@SuppressWarnings("unchecked")
	private void process(BocDepotPosMessage msg) {
		
		Map m,resultMap=null;
		String transType = (String)msg.getField(_TLV_TRANS_TYPE_KEY).getValue();
		if("98".equals(transType)){
			
			m = TLVMappingUtils.fieldMapIntoMapForTLV(msg);
			resultMap = invokeService.discountBackout( m);
			TLVMappingUtils.mapIntoFieldMapForTLV(resultMap, msg);
			
			if("00".equals(resultMap.get(07))){
				
			}
			
		}if("99".equals(transType)){
			m = TLVMappingUtils.fieldMapIntoMapForTLV(msg);
			resultMap = invokeService.exchangeBackout( m);
			TLVMappingUtils.mapIntoFieldMapForTLV(resultMap, msg);
		}if("00".equals(transType)){
			m = TLVMappingUtils.fieldMapIntoMapForTLV(msg);
			resultMap = invokeService.exchange( m);
			TLVMappingUtils.mapIntoFieldMapForTLV(resultMap, msg);
		}
		if("01".equals(transType)){//
			m = TLVMappingUtils.fieldMapIntoMapForTLV(msg);
			resultMap = invokeService.discountCheck( m);
			TLVMappingUtils.mapIntoFieldMapForTLV(resultMap, msg);
			if("00".equals(resultMap.get(07))){
				
				
				
					m = TLVMappingUtils.fieldMapIntoMapForTLV(msg);
					resultMap = invokeService.discount( m);
					TLVMappingUtils.mapIntoFieldMapForTLV(resultMap, msg);
				
				
			}
		}
		if("02".equals(transType)){
			m = TLVMappingUtils.fieldMapIntoMapForTLV(msg);
			resultMap = invokeService.discountQuery( m);
			TLVMappingUtils.mapIntoFieldMapForTLV(resultMap, msg);
		}
		msg.getHeader().swapDirection();
	}

	public void setIsoNacChannel(MinaNacChannel isoNacChannel) {
		this.isoNacChannel = isoNacChannel;
	}

	public void setIsoResponseNotNeedToPosp(List<String> isoResponseNotNeedToPosp) {
		this.isoResponseNotNeedToPosp = isoResponseNotNeedToPosp;
	}
	public void setNeedToIso(List<String> needToIso) {
		this.needToIso = needToIso;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setInvokeService(InvokePosSerivice invokeService) {
		this.invokeService = invokeService;
	}

}
