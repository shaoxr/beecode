package com.newland.posp.boc;

import com.newland.posp.BaseHeader;

public class TBDUHeader extends BaseHeader {

	private static final int TPDU_LENGTH = 5;
	
	@Override
	public int getLength() {
		return TPDU_LENGTH;
	}

}
