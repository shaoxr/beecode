package com.newland.posp.mina.requestlistener;

/**
 * 返回POS码
 * 
 * @author lance
 * POSP_NEW
 * 2009-1-16
 */
public class ReturnPosCode {
	
	/**
	 * IST返回超时
	 */
	public static final String _IST_RESPONSE_OUTOF_TIME = "OT";
	
	/**
	 * 成功返回
	 */
	public static final String _IST_RESPONSE_SUCCESS = "00";
	
	/**
	 * IST返回失败
	 */
	public static final String _IST_RESPONSE_ERROR = "ER";
	
	/**
	 * POSP返回信息
	 */
	public static String S1="S1";//货单状态不符
	public static String S2="S2";//结账账目不平
	public static String T1="T1";//此货单不存在
	public static String T2="T2";//终端号不符
	public static String T3="T3";//终端未启用
	public static String T4="T4";//终端未登记
	public static String M1="M1";//金额不符
	public static String C1="C1";//卡号不符
	public static String C2="C2";//卡即将过期
	public static String E1="E1";//操作异常
	public static String OO="00";//操作成功
	public static String N1="N1";//支付手工已确认,货单信息从货单表已经移到了支付表,不需要冲正
	
	

}
