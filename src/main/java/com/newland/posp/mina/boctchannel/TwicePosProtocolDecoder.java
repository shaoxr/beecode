package com.newland.posp.mina.boctchannel;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.iso.ISOMsg;
import com.newland.iso.packager.ISO93BPackager;
import com.newland.message.MessageException;
import com.newland.posp.boc.BocDepotAddonPackager;
import com.newland.posp.boc.BocDepotHeader;
import com.newland.posp.boc.BocDepotPosMessage;
import com.newland.posp.boc.BocIstHeader;
import com.newland.posp.boc.BocIstPosMessage;
import com.newland.posp.boc.TBDUHeader;
import com.newland.posp.mina.boc.AbstractPosProtocolDecoder;
import com.newland.posp.mina.boc.DepotPosProtocolDecoder;
import com.newland.posp.utils.Dump;
import com.newland.tlv.TLVLengthUtils;

public class TwicePosProtocolDecoder extends AbstractPosProtocolDecoder {
	private final Logger logger = LoggerFactory.getLogger(DepotPosProtocolDecoder.class);
	
	@Override
	protected void finishMessage(ByteBuffer buffer, int length,
			ProtocolDecoderOutput out) throws Exception {
		if(checkIsIST(buffer,length)){
			finishIstPosMessage(buffer,length,out);
		}else{
			finishDepotPosMessage(buffer,length ,out);
		}
		out.flush();
	}

	private boolean checkIsIST(ByteBuffer in,int length) throws Exception{
		if(length < 5){
			logger.error("报文不足tpdu(5)长度!");
			throw new MessageException("报文不足tpdu(5)长度!");
		}
		int start = in.position();
		try{
			byte [] tpdu = new byte[5];
			in.get(tpdu);
			logger.debug("tpdu dump:"+Dump.getHexDump(tpdu));
			TBDUHeader header = new TBDUHeader();
			
			header.unpack(tpdu);
			logger.debug("unpack tpdu src:"+header.getSource());
			logger.debug("unpack tpdu dest:"+header.getDestination());
			
			if(header.getSource().equals("0009")&&header.getDestination().equals("0002")){
				return true;
			}else{ 
				return false;
			}
		}catch(Exception e){
			logger.error("check packager type failed:"+e);
			throw e;
		}finally{
			in.position(start);
		}
	}

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
	 * 解IST包
	 * @param in
	 * @param length
	 * @param out
	 * @throws Exception
	 */
	private void finishIstPosMessage(ByteBuffer in, int length,
			ProtocolDecoderOutput out) throws Exception {
		
		BocIstPosMessage msg = new BocIstPosMessage();
		BocIstHeader header = new BocIstHeader();
		byte[] buf = new byte[header.getLength()];
		in.get(buf);
		logger.debug("unpack iso header dump(ist):["+Dump.getHexDump(buf)+"]");
		header.unpack(buf);
		msg.setHeader(header);
		
		buf = new byte[length - header.getLength()];
		in.get(buf);
		logger.debug("unpack iso dump(ist):["+Dump.getHexDump(buf)+"]");
		ISO93BPackager.INSTANCE.unpack(msg, buf);
		msg.setIsoRawData(buf);
		
		out.write(msg);

	}
	/**
	 * 解BocDepot包
	 * 
	 * length应该大于10
	 * 
	 * @param in
	 * @param length
	 * @param out
	 */
	private void finishDepotPosMessage(ByteBuffer in, int length,
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
		
		logger.debug("unpack header dump(pos):["+Dump.getHexDump(buf)+"]");
		header.unpack(buf);
		msg.setHeader(header);
		
		
		int isoLength = header.getISOLength();
		int addonLength = header.getAddonLength();
		logger.debug("unpack header,got iso length(pos):"+isoLength+",addon length:"+addonLength);
		
		buf = new byte[isoLength];
		ISOMsg isoMsg = new ISOMsg();
		if (isoLength > 0) {
			in.get(buf);
			logger.debug("unpack iso dump(pos):["+Dump.getHexDump(buf)+"]");
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
		logger.debug("unpack addon dump(pos):["+Dump.getHexDump(buf)+"]");
		try {
			BocDepotAddonPackager.INSTANCE.unpack(msg, buf);
		} catch (Exception e) {
			logger.error("Unpacking Addon Message Error!", e);
			throw e;
		}
		out.write(msg);
	}
}
