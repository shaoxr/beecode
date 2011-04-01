package com.newland.posp.tlv;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

import com.newland.iso.ISOUtil;
import com.newland.message.Field;
import com.newland.message.FieldMap;
import com.newland.posp.boc.BocDepotAddonPackager;
import com.newland.tlv.TLVMsgPackager;

public class TLVMsgPackagerTest extends TestCase{
	public void testPack() throws Exception {
		TLVMsgPackager packager = new BocDepotAddonPackager();
		//flds[4] = new DateFieldPackager(17, "datetime", "yyyyMMdd-HH:mm:ss");
		FieldMap m = new FieldMap();
		System.out.println("bbbbbb"+m.getFieldNumber());
		m.setString(0, "99");
		m.setString(3, "000000");
		byte[] b=packager.pack(m);
		System.out.println(ISOUtil.hexdump(b));
	}
}
