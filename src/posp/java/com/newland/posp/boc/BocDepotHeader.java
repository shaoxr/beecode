package com.newland.posp.boc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.iso.ISOUtil;
import com.newland.posp.BaseHeader;

/**
 * 中行货运站POS报文头
 * 
 * <pre>
 *   ID		TPDU类型标识			1B  (Byte 0)
 *   目地址	目标地址				2B  (Byte 1)
 *   源地址						2B  (Byte 3)
 *   ISOLEN	金融报文长度			2B  (Byte 5)
 *   ADDONLEN 附加报文长度			2B  (Byte 7)
 * </pre>
 * 
 * @author szshen
 *
 */
public class BocDepotHeader extends BaseHeader {
	private final Logger logger = LoggerFactory.getLogger(BocDepotHeader.class);
	private static final long serialVersionUID = 8674535007934468935L;
    private static final int  LENGTH = 11;
    private static final int INDEX_APPHEADER = 5;
    private static final int INDEX_ISOLEN = 7;
    private static final int INDEX_ADDONLEN = 9;
    
	public BocDepotHeader() {
		super();
	}
	
	public BocDepotHeader(String source, String destination)
	{
		super(source, destination);
	}
	
	public BocDepotHeader(byte[] header)
	{
		super(header);
	}

	public int getLength() {
		return LENGTH;
	}
	
	public byte[] getAppHeader(){
		byte[] bytes = new byte[2];
		System.arraycopy(header, INDEX_APPHEADER, bytes, 0, 2);
		return bytes;
	}
	public void setAppHeader(byte[] bytes){
		System.arraycopy(bytes, 0, header, INDEX_APPHEADER, 2);
	}
	
	public int getISOLength() {
		return ((((int) header[INDEX_ISOLEN]) & 0xFF) << 8 ) + (((int)header[INDEX_ISOLEN+1]) & 0xFF );
	}
	
	public void setISOLength(int length) {
		header[INDEX_ISOLEN] = (byte)((length >> 8) & 0xFF);
		header[INDEX_ISOLEN + 1] = (byte)(length & 0xFF);
	}
	
	public int getAddonLength() {
		return  ((((int) header[INDEX_ADDONLEN]) & 0xFF) << 8) + (((int) header[INDEX_ADDONLEN+1]) & 0xFF);
	}
	
	public void setAddonLength(int length) {
		header[INDEX_ADDONLEN] = (byte)((length >> 8) & 0xFF);
		header[INDEX_ADDONLEN + 1] = (byte)(length & 0xFF);
		
	}
}
