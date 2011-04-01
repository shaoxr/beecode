package com.newland.posp.boc;

import com.newland.iso.ISOUtil;
import com.newland.posp.BaseHeader;

/**
 * 中行直连POS报文头
 * 
 * <pre>
 *   ID		TPDU类型标识			1B  (Byte 0)
 *   目地址	目标地址				2B  (Byte 1)
 *   源地址						2B  (Byte 3)
 *   AppTag	认证版本号				2B  (Byte 5)
 * </pre>
 * 
 * @author szshen
 *
 */
public class BocIstHeader extends BaseHeader {
    private static final long serialVersionUID = 8674535007934468935L;
    private static final int INDEX_OF_VERSION = 5;
    private static final int  LENGTH = 7;
    
	public BocIstHeader() {
		this("0000", "0000",new byte [] {13,0});
	}
	public BocIstHeader(String source, String destination){
		super(source, destination);
//		 版本认证号
		header[INDEX_OF_VERSION] 	= 13;
		header[INDEX_OF_VERSION + 1]= 0;
	}
	public BocIstHeader(byte[] header){
		super();
		header[INDEX_OF_VERSION] 	= 13;
		header[INDEX_OF_VERSION + 1]= 0;
	}
	public BocIstHeader(String source, String destination ,byte [] appHeader)
	{
		super(source, destination);
		// 版本认证号
		header[INDEX_OF_VERSION] 	= appHeader[0];
		header[INDEX_OF_VERSION + 1]= appHeader[1];
	}
	
	public BocIstHeader(byte[] header,byte [] appHeader)
	{
		super();
		// 版本认证号
		header[INDEX_OF_VERSION] 	= appHeader[0];
		header[INDEX_OF_VERSION + 1]= appHeader[1];
	}
	
	public int getLength() {
		return LENGTH;
	}
	
	public byte[] getAppHeader(){
		byte[] bytes = new byte[2];
		System.arraycopy(header, INDEX_OF_VERSION, bytes, 0, 2);
		return bytes;
	}
	public void setAppHeader(byte[] bytes){
		System.arraycopy(bytes, 0, header, INDEX_OF_VERSION, 2);
	}
	
	
	public String getVersion() {
		return ISOUtil.bcd2str(header, 5, 4, false);
	}
	
	public void setVersion(String ver) {
		byte[] d = ISOUtil.str2bcd(ver, false);
		header[5] = d[0];
		header[6] = d[1];
	}

	public byte[] pack() {
		return header;
	}
}
