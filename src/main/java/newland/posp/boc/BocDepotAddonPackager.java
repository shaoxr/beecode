package com.newland.posp.boc;

import com.newland.message.packager.IFieldPackager;
import com.newland.tlv.AmountFieldPackager;
import com.newland.tlv.DateFieldPackager;
import com.newland.tlv.GBKFieldPackager;
import com.newland.tlv.StringFieldPackager;
import com.newland.tlv.TLVMsgPackager;

public class BocDepotAddonPackager extends TLVMsgPackager {
	public final static BocDepotAddonPackager INSTANCE = new BocDepotAddonPackager();
	protected IFieldPackager fld[] = {
		    /*00*/ new StringFieldPackager(  2, "交易类型(TransType)  "),
		    /*01*/ new AmountFieldPackager(  6, "金额（Amount"),
		    /*02*/ new StringFieldPackager(  6, "终端交易流水号(traceNo)"),
		    /*03*/ new StringFieldPackager( 15, "商户号(ShopID)"),
		    /*04*/ new AmountFieldPackager(  6, "折让金额"),
		    /*05*/ new StringFieldPackager( 36, "礼券编号"),
		    /*06*/ new DateFieldPackager  ( 14, "交易时间(yyyymmddhhmmss) "),
		    /*07*/ new StringFieldPackager(  2, "交易返回码  "),
		    /*08*/ new StringFieldPackager(  2, "折扣率  "),
		    /*09*/ new StringFieldPackager(  8, "终端编号"),
		    /*10*/ new GBKFieldPackager   ( 48, "响应内容(交易成功，同时需要显示中文)"),
		    /*11*/ new StringFieldPackager( 20, "卡检查码 "),
		    /*12*/ new StringFieldPackager(  6, "终端交易批次号  ")
	};
	
	public BocDepotAddonPackager()
	{
		super();
		this.setFieldPackager(fld);
	}
}
