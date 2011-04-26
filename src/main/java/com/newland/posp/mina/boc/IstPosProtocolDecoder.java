package com.newland.posp.mina.boc;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.iso.ISOUtil;
import com.newland.iso.packager.ISO93BPackager;
import com.newland.posp.boc.BocIstHeader;
import com.newland.posp.boc.BocIstPosMessage;

public class IstPosProtocolDecoder extends AbstractPosProtocolDecoder {

	private final Logger logger = LoggerFactory.getLogger(IstPosProtocolEncoder.class);
	
	protected void finishMessage(ByteBuffer in, int length,
			ProtocolDecoderOutput out) throws Exception {
		
		BocIstPosMessage msg = new BocIstPosMessage();
		BocIstHeader header = new BocIstHeader();
		byte[] buf = new byte[header.getLength()];
		in.get(buf);
		logger.debug("IST RESPONSE HEADER:["+ISOUtil.hexdump(buf)+"]");
		header.unpack(buf);
		msg.setHeader(header);
		
		buf = new byte[length - header.getLength()];
		in.get(buf);
		logger.debug("IST RESPONSE CONTENT:["+ISOUtil.hexdump(buf)+"]");
		ISO93BPackager.INSTANCE.unpack(msg, buf);
		msg.setIsoRawData(buf);
		
		out.write(msg);

	}
}
