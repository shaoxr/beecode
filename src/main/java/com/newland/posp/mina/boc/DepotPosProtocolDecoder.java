package com.newland.posp.mina.boc;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.iso.packager.ISO93BPackager;
import com.newland.message.MessageException;
import com.newland.posp.boc.BocDepotAddonPackager;
import com.newland.posp.boc.BocDepotHeader;
import com.newland.posp.boc.BocDepotPosMessage;
import com.newland.posp.boc.BocIstPosMessage;
import com.newland.posp.utils.Dump;
import com.newland.tlv.TLVLengthUtils;

/**
 * 主体解析器
 * 
 * @author shensz
 * POSP_NEW
 * 2009-1-18
 */
public class DepotPosProtocolDecoder extends AbstractPosProtocolDecoder {
	private final Logger logger = LoggerFactory.getLogger(DepotPosProtocolDecoder.class);

	/**
	 * 判断校验码
	 * @param in
	 * @param length
	 */
	private boolean verifyCheckValue(ByteBuffer in, int length) throws Exception {
		if (length < TLVLengthUtils.getMinLength()) {
			logger.error("报文长度最少为报文头"+TLVLengthUtils.getHeaderLength()+"＋校验码（4）  "+TLVLengthUtils.CHECKED_LENGTH);
			throw new MessageException("报文长度最少为报文头"+TLVLengthUtils.getHeaderLength()+"＋校验码（4）  "+TLVLengthUtils.CHECKED_LENGTH);
		}
		
		int start = in.position();
		
		try {
			// skip the tpdu
			in.skip(5);
			//skip appheader
			in.skip(2);
			
			byte[] check = new byte[8];
			for (int i=0; i<length - TLVLengthUtils.getHeaderLength(); i++) {
				if (i < 8) 
					check[i] = in.get();
				else 
					check[i%8] ^= in.get();
			}
			for (int i=0; i<4; i++) {
				if (check[i] != in.get()) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("verify check value",e);
			throw e;
		} finally {
			in.position(start);
		}
	}

	/**
	 * length应该大于10
	 * 
	 * @param in
	 * @param length
	 * @param out
	 */
	protected void finishMessage(ByteBuffer in, int length,
			ProtocolDecoderOutput out) throws Exception {
		if (!verifyCheckValue(in, length)) {
			logger.error("Found the message checkvalue error!,ignore it.");
			in.skip(length);
			return;
		}
		
		BocDepotPosMessage msg = new BocDepotPosMessage();
		
		BocDepotHeader header = new BocDepotHeader();
		byte[] buf = new byte[header.getLength()];
		in.get(buf);
		header.unpack(buf);
		msg.setHeader(header);
		int isoLength = header.getISOLength();
		int addonLength = header.getAddonLength();
		
		logger.debug("tpdu unpack src(main package):"+header.getSource());
		logger.debug("tpdu unpack dest(main package):"+header.getDestination());
		logger.debug("unpack header(main package):"+Dump.getHexDump(buf));
		logger.debug("unpack iso-length(main package):"+isoLength);
		logger.debug("unpack addon-length(main package):"+addonLength);
		
		buf = new byte[isoLength];
		BocIstPosMessage isoMsg = new BocIstPosMessage();

		if (isoLength > 0) {
			in.get(buf);
			logger.debug("unpack iso(main package) dump:["+Dump.getHexDump(buf)+"]");
			try {
				ISO93BPackager.INSTANCE.unpack(isoMsg, buf);
			}catch (Exception e) {
				logger.error("Unpacking Iso Message error!", e);
				throw e;
			}
		}
		msg.setIsoMessage(isoMsg);
		msg.setIsoRawData(buf);
		buf = new byte[addonLength];
		in.get(buf);
		logger.debug("unpack addon(main package) dump["+Dump.getHexDump(buf)+"]");
		
		
		try {
			BocDepotAddonPackager.INSTANCE.unpack(msg, buf);
		} catch (Exception e) {
			logger.error("Unpacking Addon Message Error!", e);
			throw e;
		}
		out.write(msg);
	}
}
