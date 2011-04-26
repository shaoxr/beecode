package com.newland.posp;

import com.newland.iso.ISOUtil;

/**
 * 用于固定长度报文头
 * @author szshen
 *
 */
public abstract class BaseHeader implements Header {
	private static final long serialVersionUID = 1L;
	
    protected byte[] header;
    
    
    /**
     * 中国银行总行IST
     */
    public static final String _TPDU_BOC_IST = "0009";
    
    public static final String _TPDU_BOC_POSP = "0000";

    public BaseHeader()
    {
        header = new byte[getLength()];
        header[0] = 0x60;
    }
    
    public BaseHeader(String source, String destination) {
    	header = new byte[getLength()];
    	header[0] = 0x60;
    	setSource(source);
    	setDestination(destination);
    }

    /**
     * Create a new Header from a byte array.
     */
    public BaseHeader (byte[] header) {
        unpack(header);
    }

    public byte[] pack() {
        return header;
    }

    public int unpack (byte[] header) {
    	if (header != null && header.length >= this.getLength()) {
    		this.header = new byte[getLength()];
    		System.arraycopy(header, 0, this.header, 0, getLength());
    		return this.getLength();
    	}
        return 0;
    }

    public abstract int getLength () ;
    
    /**
     * 标准报文头TPDU的目地址为第二个字节开始的两个字节
     * @return
     */
    protected int getIndexOfDestination() {
    	return 1;
    }
    
    /**
     * 标准报文头TPDU的源地址为第4个字节开始的两个字节
     * @return
     */
    protected int getIndexOfSource() {
    	return 3;
    }
    
    public void setDestination(String dst) {
    	byte[] d = ISOUtil.str2bcd(dst, true);
        System.arraycopy(d, 0, header, this.getIndexOfDestination(), 2);
    }
    public void setSource(String src) {
    	byte[] d = ISOUtil.str2bcd(src, true);
        System.arraycopy(d, 0, header, this.getIndexOfSource(), 2);
    }
    
    public String getDestination() { 
    	return ISOUtil.bcd2str (this.header, this.getIndexOfDestination(), 4, false);
    }
    
    public String getSource() { 
    	return ISOUtil.bcd2str (this.header, this.getIndexOfSource(), 4, false);
    }
    
    public void swapDirection() {
    	if (header != null && header.length >= getLength()) {
                byte[] source = new byte[2];
                System.arraycopy(header, getIndexOfSource(), source, 0, 2);
                System.arraycopy(header, getIndexOfDestination(), header, getIndexOfSource(), 2);
                System.arraycopy(source, 0, header, getIndexOfDestination(), 2);
            }
    }

}
