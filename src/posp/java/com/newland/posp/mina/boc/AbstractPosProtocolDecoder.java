package com.newland.posp.mina.boc;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.posp.utils.Dump;

public abstract class AbstractPosProtocolDecoder extends CumulativeProtocolDecoder {
	private final Logger logger = LoggerFactory.getLogger(AbstractPosProtocolDecoder.class);
	protected boolean doDecode(IoSession session, ByteBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		
		if (in.remaining() > 2) {
			// Remember the initial position.
			int start = in.position();
			int len = getLength(in);
			logger.debug("unpack total length:"+len);
			logger.debug("remaining:"+in.remaining());
			if (in.remaining() >= len) {
				finishMessage(in, len, out);
				return true;
			} 
			
			if(in.remaining() < 3){//读空嗅探包   
				byte b[] = new byte[in.remaining()];
				in.get(b);
				logger.info("got test package:["+Dump.getHexDump(b)+"]");
				return false;
			}
			// 报文长度不够
			in.position(start);
			return false;
		} else {
			return false;
		}
	}

	protected int getLength(ByteBuffer in) {
		byte [] len = new byte[2];
		in.get(len);
		
		logger.debug("length dump:"+Dump.getHexDump(len));
		return  ((((int) len[0]) & 0xFF) << 8) + (((int) len[1]) & 0xFF)  ;
	}
	
	protected abstract void finishMessage(ByteBuffer buffer, int length, ProtocolDecoderOutput out) throws Exception;
}
