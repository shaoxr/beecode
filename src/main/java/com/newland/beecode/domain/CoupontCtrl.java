package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class CoupontCtrl {

	/**
	 * 已冲正
	 */
	public static final String VOID_FLAG_BACKOFF = "0";
	
	/**
	 * 正常
	 */
	public static final String VOID_FLAG_NORMAL = "1";
	
	/**
	 * 礼券编号
	 */
    private Long couponId;

    /**
     * 随机串号（二维码文本）
     */
    @Size(max = 45)
    private String serialNo;
    
    /**
     * 终端流水号
     */
    private String traceNo;
    
    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 编码版本号（二维码文本编码格式版本）
     */
    @NotNull
    @Size(max = 2)
    private String encodeVersion;

    /**
     * 商户终端编号
     */
    @NotNull
    @Size(max = 16)
    private String deviceNo;

    /**
     * 商户编号
     */
    @NotNull
    @Size(max = 32)
    private String partnerNo;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    private Date checkDay;

    /**
     * 检验日期
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date checkDate;

    /**
     * 折扣金额或兑换物品价值
     */
    private BigDecimal amount;

    /**
     * 折扣率
     */
    private BigDecimal rebateRate;
    
    /**
     * 业务代码
     */
    @NotNull
    @Size(max = 2)
    private String businessType;
    
    /**
     * 冲正标志 
     * 0 - 冲正  
     * 1 - 未冲正
     */
    private String voidFlag;
    /**
     * 
     * @param serialNo
     * @return
     */
    public static List findBySerialNo(String serialNo) {
    	Query q = entityManager()
		.createQuery(
				"select couponCtrl from CoupontCtrl as couponCtrl where couponCtrl.serialNo=:serialNo");
        q.setParameter("serialNo", serialNo);
        return (List) q.getResultList();
    }
    
}
