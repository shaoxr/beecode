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
	 * 该卡不参与活动
	 */
	public static final String ERR_COUPON_CARD_NOT_USE="BIZ.CARD.000";
	
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
	 * 该卡不参与活动 
	 */
	public static final String POSP_ERR_COUPON_CARD_DO_NOT_USE="E6";
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
	 * 客户记录为空
	 */
	public static final String BIZ_CUSTOMER_BLANK="BIZ.022";
	/**
	 * 起始日期迟于截止日期
	 */
	public static final String BIZ_STARDATE_AFTER_ENDDATE="BIZ.023";
	
	/**
	 * 客户资料检查，卡号位数不对
	 */
	public static final String BIZ_CUSTOMER_ACCOUNT_LENGTH_ERROR="BIZ.024";
	/**
	 * 客户资料检查，卡号格式不对(包含非数字)
	 */
	public static final String BIZ_CUSTOMER_ACCOUNT_NOT_NUMBER="BIZ.025";
	/**
	 * 客户资料检查，金额为空
	 */
	public static final String BIZ_CUSTOMER_AMOUNT_NULL="BIZ.026";
	/**
	 * 客户资料检查，金额格式不对(包含非数字)
	 */
	public static final String BIZ_CUSTOMER_AMOUNT_NOT_NUMBER="BIZ.027";
	/**
	 * 客户资料检查，兑换物品名称为空
	 */
	public static final String BIZ_CUSTOMER_EXCHANGE_NAME_NULL="BIZ.035";
	
	public static final String BIZ_CUSTOMER_EXCHANGE_AMOUNT_NULL="BIZ.028";
	
	public static final String BIZ_CUSTOMER_EXCHANGE_AMOUNT_NOT_NUMBER="BIZ.029";
	
	public static final String BIZ_CUSTOMER_REBATE_RATE_NULL="BIZ.030";
	
	public static final String BIZ_CUSTOMER_REBATE_RATE_NOT_NUMBER="BIZ.031";
	
	public static final String BIZ_CUSTOMER_REBATE_RATE_ERROR="BIZ.032";
	
	public static final String BIZ_CUSTOMER_BACKAMOUNT_NULL="BIZ.033";
	
	public static final String BIZ_CUSTOMER_BACKAMOUNT_ERROR="BIZ.034";
	
	/**
	 * 商户类别名称已经存在
	 */
	public static final String BIZ_PARTNERCATALOG_NAME_EXITS="BIZ.PARTNERCATALOG.001";
	/**
	 * 商户类别下已经存在商户，无法删除
	 */
	public static final String BIZ_PARTNERCATALOG_DONOT_DELETE="BIZ.PARTNERCATALOG.002";
	/**
	 * 商户名或者商户号已经存在
	 */
	public static final String BIZ_PARTNER_NAME_OR_NO_EXITS="BIZ.PARTNER.000";
	/**
	 * 商户名已经存在
	 */
	public static final String BIZ_PARTNER_NAME_EXITS="BIZ.PARTNER.001";
	
	/**
	 * 商户号已经存在
	 */
	public static final String BIZ_PARTNER_NO_EXITS="BIZ.PARTNER.002";
	/**
	 * 找到商户sheet
	 */
	public static final String BIZ_PARTNER_SHEET_NOT_FOUND="BIZ.PARTNER.003";
	/**
	 * EXCEL商户名不能为空
	 */
	public static final String BIZ_PARTNER_EXCEL_NAME_NULL="BIZ.PARTNER.004";
	/**
	 * EXCEL商户编号不能为空或者格式有错
	 */
	public static final String BIZ_PARTNER_EXCEL_PARTNERNO_ERROR="BIZ.PARTNER.005";
	/**
	 *  EXCEL商户检查通过
	 */
	public static final String BIZ_PARTNER_EXCEL_PASS="BIZ.PARTNER.006";
	/**
	 * 文件上传有误
	 */
	public static final String BIZ_PARTNER_EXCEL_FILE_UPLOAD_ERROR="BIZ.PARTNER.007";
	/**
	 * 商户文件格式有误
	 */
	public static final String BIZ_PARTNER_EXCEL_CHECK_ERROR="BIZ.PARTNER.008";
	/**
	 * 商户不存在
	 */
	public static final String BIZ_PARTNER_EXCEL_NOT_EXITS="BIZ.PARTNER.009";
	/**
	 * 商户文件检查不通过
	 */
	public static final String BIZ_PARTNER_EXCEL_CHECK_NOT_PASS="BIZ.PARTNER.010";
	/**
	 * 商户文件记录为空
	 */
	public static final String BIZ_PARTNER_EXCEL_BLANK="BIZ.PARTNER.011";
	/**
	 * 该商户已经绑定终端，无法删除，
	 */
	public static final String BIZ_PARTNER_DO_NOT_DELETE="BIZ.PARTNER.012";
	
	
	/**
	 * 终端号已经存在
	 */
	public static final String BIZ_PARTNER_TERMINAL_NO_EXITS="BIZ.PARTNER.TERMINAL.000";
	/**
	 * 终端名已经存在
	 */
	public static final String BIZ_PARTNER_TERMINAL_NAME_EXITS="BIZ.PARTNER.TERMINAL.001";
	/**
	 * 该终端已经参与活动，无法删除
	 */
	public static final String BIZ_PARTNER_TERMINAL_DO_NOT_DELETE="BIZ.PARTNER.TERMINAL.002";
	
	
	/**
	 * EXCEL终端编号不能为空或者格式有错
	 */
	public static final String BIZ_TERMINAL_EXCEL_TERMINALNO_ERROR="BIZ.PARTNER.TERMINAL.003";
	/**
	 * 商户不存在
	 */
	public static final String BIZ_TERMINAL_EXCEL_NOT_EXITS="BIZ.PARTNER.TERMINAL.004";
	
	
	
	
	
	/**
	 * 只有在待审核或者审核失败情况下才可以更新
	 */
	public static final String BIZ_MARKETINGACT_DOT_NO_UPDATE="BIZ.MARKETINGACT.000";
	
	/**
	 * 没有待审核客户文件，无法提交审核
	 */
	public static final String BIZ_MARKETINGACT_CUSTOMER_FILE_NULL_DO_NOT_SUBMIT_CHECK="BIZ.MARKETINGACT.001";
	
	/**
	 * 卡bin检查码不能为空
	 */
	public static final String BIZ_MARKETINGACT_CHECK_CODE_NOT_NULL="BIZ.MARKETINGACT.002";
	/**
	 * 卡bin检查码格式不对，只能为数字或者*号，长度为6
	 */
	public static final String BIZ_MARKETINGACT_CHECK_CODE_FORMAT_ERROR="BIZ.MARKETINGACT.003";
	/**
	 * *必须要跟在数字后面
	 */
	public static final String BIZ_MARKETINGACT_CHECK_CODE_ORDER_ERROR="BIZ.MARKETINGACT.004";
	/**
	 * 字数超过280
	 */
	public static final String BIZ_MARKETINGACT_MMS_CONTENT_NUM_OVER="BIZ.MARKETINGACT.005";
	
	
	
	
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
	/**
	 * 操作员两次输入密码不一样
	 */
	public static final String BIZ_OPER_PWD_INCORRECT="BIZ.OPER.002";
	/**
	 * 操作员角色不能为空
	 */
	public static final String BIZ_OPER_ROLE_NULL="BIZ.OPER.003";
	/**
	 * 客户重复
	 */
	public static final String BIZ_CUSTOMER_EXITS="BIZ.CUSTOMER.000";
	/**
	 * 二维码生成错误
	 */
	public static final String BIZ_BARCODE_GEN_ERROR="BIZ.BARCODE.000";
	
	
	/**
	 * 找到不文件
	 */
	public static final String BIZ_IO_FILE_NOT_FOUND="BIZ.IO.000";
	
	/**
	 * 文件操作失败
	 */
	public static final String BIZ_IO_EXCEPTION="BIZ.IO.001";
	/**
	 * 短信正在发送中，请稍候再操作
	 */
	public static final String BIZ_SMS_SENDING="BIZ.SMS.000";
	/**
	 * 彩信正在发送中，请稍候再操作
	 */
	public static final String BIZ_MMS_SENDING="BIZ.MMS.000";
	
	public static final String BIZ_MMS_SUBMIT_SUCCESS="BIZ.MMS.001";
	/**
	 * 发送时找不到彩信文件
	 */
	public static final String BIZ_MMS_SEND_ACT_FILE_NOT_FOUND="BIZ.MMS.002";
	/**
	 * 没有需要发送的彩信文件
	 */
	public static final String BIZ_MMS_NOT_COUPON_TO_SEND="BIZ.MMS.003";
	/**
	 * 彩信余额不足
	 */
	public static final String BIZ_MMS_LACK_OF_BALANCE="BIZ.MMS.004";
	/**
	 * 彩信网络连接异常，请检查网络
	 */
	public static final String BIZ_MMS_CONNECT_ERROR="BIZ.MMS.005";
	/**
	 * 彩信用户名或者密码
	 */
	public static final String BIZ_MMS_NAME_ERROR="BIZ.MMS.006";
	/**
	 * 彩信发送出错,文件格式有误
	 */
	public static final String BIZ_MMS_SEND_ERROR_FILE="BIZ.MMS.007";
	/**
	 * 彩信发送成功
	 */
	public static final String BIZ_MMS_SEND_SUCCESS="BIZ.MMS.008";
	/**
	 * 更新彩信/短信文件时出错，文件格式不对
	 */
	public static  final String BIZ_MMS_RESP_FILE_ERROR="BIZ.MMS.RESP.000";
	
	
	
	
	
	
	
	
	
	public static final String BIZ_SMS_SUBMIT_SUCCESS="BIZ.SMS.001";
	/**
	 * 发送时找不到短信文件
	 */
	public static final String BIZ_SMS_SEND_ACT_FILE_NOT_FOUND="BIZ.SMS.002";
	/**
	 * 没有需要发送的短信文件
	 */
	public static final String BIZ_SMS_NOT_COUPON_TO_SEND="BIZ.SMS.003";
	
	/**
	 * 短信余额不足
	 */
	public static final String BIZ_SMS_LACK_OF_BALANCE="BIZ.SMS.004";
	/**
	 * 短信网络连接异常，请检查网络
	 */
	public static final String BIZ_SMS_CONNECT_ERROR="BIZ.SMS.005";
	/**
	 * 短信用户名或密码错误
	 */
	public static final String BIZ_SMS_NAME_ERROR="BIZ.SMS.006";
	/**
	 * 短信发送时出错,文件格式有误
	 */
	public static final String BIZ_SMS_SEND_ERROR="BIZ.SMS.007";
	
	
	
	
	/**
	 * 状态反馈文件解析有误
	 */
	public static final String BIZ_RESP_STATUS_ERROR="BIZ.RESP_STATUS.000";
	/**
	 * 找不到sheet
	 */
	public static final String BIZ_RESP_STATUS_SHEET_NOT_FOUND="BIZ.RESP_STATUS.001";
	/**
	 * 文件内容为空
	 */
	public static final String BIZ_RESP_STATUS_EXCEL_BLANK="BIZ.RESP_STATUS.002";
	
	/**
	 * 正在发送中，无法下载
	 */
	public static final String BIZ_SEND_LIST_DO_NOT_DOWNLOAD="BIZ.SEND_LIST.000";
	
	
	
	
}
