package com.newland.beecode.exception;

public class ErrorsCode {
/**
 * 
 * 
 * 
 * **************************************pos交易相关错误码
 */
	/**
	 * 礼券不存在
	 */
	public static final String ERR_COUPON_NOT_EXIST = "BIZ.001";
	
	/**
	 * 礼券已过期
	 */
	public static final String ERR_COUPON_EXPIRED = "BIZ.002";
	
	/**
	 * 礼券已失效（挂失/活动关闭）
	 */
	public static final String ERR_COUPON_INVALID = "BIZ.003";
	
	/**
	 * 礼券在该商户不可用
	 */
	public static final String ERR_COUPON_PARTNER_NOT_FOUND = "BIZ.004";
	
	/**
	 * posp 返回错误码
	 * 礼券不存在
	 */
	public static final String POSP_ERR_COUPON_NOT_EXIST = "E1";
	/**
	 * posp 返回错误码
	 * 礼券已失效
	 */
	public static final String POSP_ERR_COUPON_INVALID = "E2";
	/**
	 * posp 返回错误码
	 * 礼券已过期
	 */
	public static final String POSP_ERR_COUPON_EXPIRED = "E4";
	
	/**
	 * posp 返回错误码
	 * 礼券在该商户不可用
	 */
	public static final String POSP_ERR_COUPON_PARTNER_NOT_FOUND="E5";
	/**
	 * posp 返回错误码
	 * 系统异常
	 */
	public static final String POSP_ERR_SYSTEM_EEROR = "E3";
	/**
	 * posp 正常返回码
	 */
	public static final String POSP_RIGHT = "00";
	
	public static final String SYSTEM_ERR="BIZ.005";
	/**
	 * 
	 * 
	 * 
	 * *******************************业务逻辑错误码
	 */
	public static final String MESSAGE="message";
	/**
	 * 活动已经审核无法作废 
	 */
	public static final String BIZ_MARKETACT_DONOT_DELETE="BIZ.006";
	/**
	 * 客户文件上传失败
	 */
	public static final String BIZ_FILE_UPLOAD_ERROR="BIZ.007";
	/**
	 * 礼券发放失败
	 */
	public static final String BIZ_COUPON_GIVE_ERROR="BIZ.008";
	/**
	 * 礼券生成失败
	 */
	public static final String BIZ_COUPON_GEN_ERROR="BIZ.020";
	
	/**
	 * 活动已经审核
	 */
	public static final String BIZ_COUPON_CHECKED="BIZ.009";
	/**
	 * 彩信平台连接异常
	 */
	public static final String BIZ_MMS_CON_ERROR="BIZ.010";
	/**
	 * 活动礼券已经发放
	 */
	public static final String BIZ_MARKETING_GIVED="BIZ.011";
	/**
	 * 信息发送连接异常
	 */
	public static final String BIZ_MS_SEND_ERROR="BIZ.012";
	/**
	 * 余额不足
	 */
	public static final String BIZ_MS_BALANCE_UN_LESS="BIZ.013";
	/**
	 * 
	 */
	public static final String BIZ_COUSTMER_NAME_NULL="BIZ.014";
	
	public static final String TEST="test";
	
	public static final String BIZ_COUSTMER_MOBILE_ERROR="BIZ.015";
	
	public static final String BIZ_CUSTOMER_PASS="BIZ.016";
	/**
	 * 客户资料文件解析出错
	 */
	public static final String BIZ_CUSTOMER_FILE_CHECK_ERROR="BIZ.017";
	/**
	 * 文件上传失败
	 */
	public static final String BIZ_CUSTOMER_FILE_UPLOAD_ERROR="BIZ.018";
	/**
	 * sheet找不到
	 */
	public static final String BIZ_CUSTOMER_SHEET_NOT_FIND="BIZ.019";
	/**
	 * 创建活动时，客户资料检查不通过
	 */
	public static final String BIZ_CUSTOMER_CREATE_ERROR="BIZ.021";
	/**
	 * 商户类别名称已经存在
	 */
	public static final String BIZ_PARTNERCATALOG_NAME_EXITS="BIZ.PARTNERCATALOG.001";
	/**
	 * 商户类别下已经存在商户，无法删除
	 */
	public static final String BIZ_PARTNERCATALOG_DONOT_DELETE="BIZ.PARTNERCATALOG.002";
	
	/**
	 * 商户名已经存在
	 */
	public static final String BIZ_PARTNER_NAME_EXITS="BIZ.PARTNER.001";
	
	/**
	 * 商户号已经存在
	 */
	public static final String BIZ_PARTNER_NO_EXITS="BIZ.PARTNER.002";
	
	
	/**
	 * 活动类别下已经存在活动，无法删除
	 */
	public static final String BIZ_MARKINGCATALOG_DONOT_DELETE="BIZ.MARKETINGCATALOG.001";
	
	/**
	 * 活动类别已经存在
	 */
	public static final String BIZ_MARKINGCATALOG_NAME_EXITS="BIZ.MARKETINGCATALOG.002";
	
	/**
	 * 操作员姓名已经存在
	 */
	public static final String BIZ_OPER_NAME_EXITS="BIZ.OPER.001";
	
}
