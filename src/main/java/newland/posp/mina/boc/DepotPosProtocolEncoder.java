package com.newland.posp.mina.boc;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.iso.ISOUtil;
import com.newland.posp.boc.BocDepotAddonPackager;
import com.newland.posp.boc.BocDepotPosMessage;
import com.newland.tlv.TLVLengthUtils;

public class DepotPosProtocolEncoder extends AbstractPosProtocolEncoder {
	private final static Logger log = LoggerFactory.getLogger(DepotPosProtocolEncoder.class);

	public void encode(IoSession session, Object message,
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
