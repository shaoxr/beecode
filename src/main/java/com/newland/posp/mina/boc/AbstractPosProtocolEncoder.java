package com.newland.posp.mina.boc;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;

public abstract class AbstractPosProtocolEncoder extends ProtocolEncoderAdapter {
	protected void putLength(ByteBuffer buffer, int length) {
		byte theByte = (byte) ((length >> 8) & 0xFF);
		buffer.put(theByte);
		buffer.put((byte) (length & 0xFF));
	}
}