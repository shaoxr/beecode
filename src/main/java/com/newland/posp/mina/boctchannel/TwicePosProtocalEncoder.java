package com.newland.posp.mina.boctchannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.newland.iso.ISOUtil;
import com.newland.iso.packager.ISO93BPackager;
import com.newland.message.MessageException;
import com.newland.posp.boc.BocDepotAddonPackager;
import com.newland.posp.boc.BocDepotPosMessage;
import com.newland.posp.boc.BocIstPosMessage;
import com.newland.posp.mina.boc.AbstractPosProtocolEncoder;
import com.newland.tlv.TLVLengthUtils;

/**
 * 
 * @author lance
 * POSP_NEW
 * 2009-1-19
 */
public class TwicePosProtocalEncoder extends AbstractPosProtocolEncoder{

	public Log log = LogFactory.getLog(TwicePosProtocalEncoder.class);
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		try{
			if(message instanceof BocDepotPosMessage){
				BocDepotPosMessage msg = (BocDepotPosMessage) message;
				encode(session,msg,out);
			}else if(message instanceof BocIstPosMessage){
				BocIstPosMessage msg = (BocIstPosMessage) message;
				encode(session,msg,out);
			}else 
				throw new  MessageException("unsurpported message!"+message.getClass().getName());
		}catch(Exception e){
			log.error("encode failed!"+e);
			throw (e);
		}finally{
			session.close();
		}
		
		
	}
	
	/**
	 * 组IST包
	 * @param session
	 * @param message
	 * @param out
	 * @throws Exception
	 */
	public void encode(IoSession session, BocIstPosMessage message,
			ProtocolEncoderOutput out) throws Exception {
		BocIstPosMessage msg = (BocIstPosMessage) message;

		byte[] isoMsg = msg.getIsoRawData();
		if (isoMsg == null) {
			// Has not rawdata need pack
			isoMsg = ISO93BPackager.INSTANCE.pack(msg);
		}
		byte[] header = msg.getHeader().pack();

		if (log.isDebugEnabled()) {
			log.debug("encoded header\n" + ISOUtil.hexdump(header));
			log.debug("encoded iso\n" + ISOUtil.hexdump(isoMsg));
		}

		// length + header + 8583 msg + addon msg + checkvalue(4)
		int capacity = 2 + header.length + isoMsg.length;

		ByteBuffer buffer = ByteBuffer.allocate(capacity, false);
		putLength(buffer, capacity - 2);
		buffer.put(header);
		buffer.put(isoMsg);
		buffer.flip();
		out.write(buffer);
		out.flush();
		
	}
	
	/**
	 * 组发POS包
	 * @param session
	 * @param message
	 * @param out
	 * @throws Exception
	 */
	public void encode(IoSession session, BocDepotPosMessage message,
			ProtocolEncoderOutput out) throws Exception {
		try {
			BocDepotPosMessage msg = (BocDepotPosMessage) message;

			byte[] isoMsg = msg.getIsoRawData();
			byte[] addon = BocDepotAddonPackager.INSTANCE.pack(msg);
			msg.getHeader().setAddonLength(addon.length);
			msg.getHeader().setISOLength(isoMsg.length);
			byte[] header = msg.getHeader().pack();
			// TODO 交易系统日志和数据库不能保存任何持卡人私密信息
			if (log.isDebugEnabled()) {
				log.debug("encoded header\n" + ISOUtil.hexdump(header));
				log.debug("encoded iso\n" + ISOUtil.hexdump(isoMsg));
				log.debug("encoded addon\n" + ISOUtil.hexdump(addon));
			}

			// length + header + 8583 msg + addon msg + checkvalue(4)
			int capacity = 2 + TLVLengthUtils.getHeaderLength() + isoMsg.length + addon.length + 4;

			ByteBuffer buffer = ByteBuffer.allocate(capacity, false);
			putLength(buffer, capacity - 2);
			buffer.put(header);
			buffer.put(isoMsg);
			buffer.put(addon);
			appendCheckValue(buffer, 9, capacity - 4);
			buffer.flip();
			out.write(buffer);
			out.flush();
			
		} catch (Exception e) {
			log.error("encoding", e);
			throw e;
		}
	}

	/**
	 * 计算校验码，并将校验码添加到发送缓冲中 校验码从TPDU后开始计算，每8个字节异或，取前四字节
	 * 
	 * @param buffer
	 * @param from
	 * @param to
	 */
	protected void appendCheckValue(ByteBuffer buffer, int from, int to) {
		byte[] check = new byte[8];
		buffer.position(from);
		for (int i = 0; i < to - from; i++) {
			if (i < 8)
				check[i] = buffer.get();
			else {
				check[i % 8] ^= buffer.get();
			}
		}
		for (int i = 0; i < 4; i++) {
			buffer.put(check[i]);
		}
	}
}
