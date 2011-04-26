package com.newland.tlv;

public class TLVLengthUtils {
	

	public static final int TPDU_LENGTH = 5;
	public static final int APPHEADER_LENGTH = 2;
	public static final int ISO_LENGTH = 2;
	public static final int ADDON_LENGTH = 2;
	
	
	public static final int CHECKED_LENGTH = 4;

	
	public static int getMinLength(){
		return getHeaderLength()+CHECKED_LENGTH;
	}
	public static int getHeaderLength(){
		return TPDU_LENGTH+APPHEADER_LENGTH+ISO_LENGTH+ADDON_LENGTH;
	}
	public static int getAppheaderLength(){
		return TPDU_LENGTH;
	}
	public static int getISOIndex(){
		return TPDU_LENGTH+APPHEADER_LENGTH;
	}
	public static int getAddonIndex(){
		return TPDU_LENGTH+APPHEADER_LENGTH+ISO_LENGTH;
	}
}
