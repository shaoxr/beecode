package com.newland.tlv;

import com.newland.iso.ISOUtil;
import com.newland.message.Field;
import com.newland.message.IField;
import com.newland.message.MessageException;

public class IntHexFieldPackager extends TLVFieldPackager {
	public IntHexFieldPackager() {
		this(-1, null);
	}

	public IntHexFieldPackager(int length, String desc) {
		super(length, desc);
	}

	@Override
	public IField<?> createComponent(int fldNo) {
		return new Field<Integer>(fldNo);
	}

	@Override
	protected String getAsString(IField<?> field) {
		int value = ((IField<Integer>)field).getValue();
		byte[] b = new byte[2];
		b[1] = (byte)(value & 0xFF);
		b[0] = (byte)((value>>8) & 0xFF);
		return ISOUtil.hexString(b);
	}

	@Override
	protected void setFieldValue(IField<?> field, String value)
			throws MessageException {
		
		byte[] b = ISOUtil.hex2byte(value);
		
		int intValue = 0; 
		for (int i=0; i<b.length; i++) {
			int tmp = b[i];
			intValue = intValue << 8 + (tmp & 0xFF);
		}
		((IField<Integer>)field).setValue(intValue);
	}

}
