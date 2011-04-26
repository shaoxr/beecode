package com.newlandcomputer.posp.txn;

import java.util.Map;

public interface InvokePosSerivice {
	//兑换券冲正
    public Map exchangeBackout(Map reqMap );
    //兑换券兑换
    public Map exchange(Map reqMap );
    //折扣验证
    public Map discountCheck(Map reqMap );
    //折扣登记
    public Map discount(Map reqMap );
    //折扣冲正
    public Map discountBackout(Map reqMap );
    //折扣券查询
    public Map discountQuery(Map reqMap);
}
