package com.newland.posp.boc;

import com.newland.posp.Message;

public class BocIstPosMessage extends Message {
	private static final long serialVersionUID = 1L;
	private byte[] isoRawData;
	
	public BocIstHeader getHeader() {
		if (header == null)
			header = new BocIstHeader();
		return (BocIstHeader)header;
	}
	
	public byte[] getIsoRawData() {
		return isoRawData;
	}

	public void setIsoRawData(byte[] isoRawData) {
		this.isoRawData = isoRawData;
	}
}
