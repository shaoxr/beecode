package com.newland.tlv;

import com.newland.iso.GBKInterpreter;

public class GBKFieldPackager extends StringFieldPackager {

	public GBKFieldPackager() {
		this(-1, null);
	}

	public GBKFieldPackager(int len, String desc) {
		super(len, desc);
		this.setInterpreter(GBKInterpreter.INSTANCE);
	}
}
