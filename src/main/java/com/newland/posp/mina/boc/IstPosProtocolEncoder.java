package com.newland.posp.mina.boc;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.iso.ISOUtil;
import com.newland.iso.packager.ISO93BPackager;
import com.newland.posp.boc.BocIstPosMessage;

public class IstPosProtocolEncoder extends AbstractPosProtocolEncoder {

	private final Logger logger = LoggerFactory.getLogger(IstPosProtocolEncoder.class);

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		BocIstPosMessage msg = (BocIstPosMessage) message;

		byte[] isoMsg = msg.getIsoRawData();
		if (isoMsg == null) {
			// Has not rawdata need pack
			isoMsg = ISO93BPackager.INSTANCE.pack(msg);
		}
		byte[] header = msg.getHeader().pack();

		if (logger.isDebugEnabled()) {
			logger.debug("send to ist: encoded header\n" + ISOUtil.hexdump(header));
			logger.debug("send to ist: encoded iso\n" + ISOUtil.hexdump(isoMsg));
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
}
