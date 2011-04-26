package com.newland.posp.boc;

import com.newland.message.FieldMap;
import com.newland.posp.Message;

/**
 *  中行吉林分行货运POS支付系统的POS报文规范
 *  [LEN][TPDU][95][LEN of 直连POS报文][LEN of货运交易报文][直连POS报文][货运交易报文][验证码] 
 *  
 *  根据AppTag可以判断应用类型或者版本
 *  目前银联定义了以下四种类型
 *  	磁条卡金融支付类应用为：	60
 *  	IC卡金融支付类应用为：  	61
 *  	磁条卡增值业务类支付为：	62
 *  	IC卡增值业务类支付为： 	63
 *  中行AppTag为版本号（2个字节）
 *  	0100～0131
 *  	0300
 *  	1300
 *  	0303
 *  	1303
 *  吉林中行货运交易
 *  	95
 */
public class BocDepotPosMessage extends Message {
	public Throwable getException() {
		return this.exception;
	}
	
	public void setException(Throwable e) {
		this.exception = e;
	}
	
	public BocDepotHeader getHeader() {
		if (header == null)
			header = new BocDepotHeader();
		return (BocDepotHeader)header;
	}
	
	public byte[] getIsoRawData() {
		return isoRawData == null?new byte[0]:isoRawData;
	}
	
	public void setIsoRawData(byte[] isoRawData) {
		this.isoRawData = isoRawData;
	}
	
	public FieldMap getIsoMessage() {
		return isoMsg;
	}
	
	public void setIsoMessage(FieldMap isoMsg) {
		this.isoMsg = isoMsg;
	}
	
	private static final long serialVersionUID = 1L;
	private byte[] isoRawData;
	private FieldMap isoMsg;
	private Throwable exception;
}
