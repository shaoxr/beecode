package com.newland.beecode.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.utils.NewlandUtil;
@Entity
public class MmsTemplate {
	public static final String NAME="XXXname";
	
	public static final String START_DATE="SYYYY-MM-DD";
	
	public static final String END_DATE="EYYYY-MM-DD";
	
	public static final String VALUE="VALUE";
	
	public static final String COUPONID="XXXXXXXXXXXX";
	
	public static final String ACCOUNT_4="XXXXcard";
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "0") })
	@Column
	private Long id;
    @Column
	private String title1;
    @Column
	private String title2;
    @Column
	private String cardBefore;
    @Column
	private String couponIdBefore;
    @Column
	private String valueBefore;
    @Column
	private String periodBefore;
    @Column
	private String ending;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle1() {
		return title1;
	}
	public void setTitle1(String title1) {
		this.title1 = title1;
	}
	public String getTitle2() {
		return title2;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
	public String getCardBefore() {
		return cardBefore;
	}
	public void setCardBefore(String cardBefore) {
		this.cardBefore = cardBefore;
	}
	public String getCouponIdBefore() {
		return couponIdBefore;
	}
	public void setCouponIdBefore(String couponIdBefore) {
		this.couponIdBefore = couponIdBefore;
	}
	public String getValueBefore() {
		return valueBefore;
	}
	public void setValueBefore(String valueBefore) {
		this.valueBefore = valueBefore;
	}
	public String getPeriodBefore() {
		return periodBefore;
	}
	public void setPeriodBefore(String periodBefore) {
		this.periodBefore = periodBefore;
	}
	public String getEnding() {
		return ending;
	}
	public void setEnding(String ending) {
		this.ending = ending;
	}
	public String toString(){
		StringBuffer buf=new StringBuffer();
		buf.append(this.getTitle1());
		buf.append(MmsTemplate.NAME);
		buf.append(this.getTitle2());
		buf.append(this.getCardBefore());
		buf.append(MmsTemplate.ACCOUNT_4);
		buf.append(this.getValueBefore());
		buf.append(MmsTemplate.VALUE);
		buf.append(this.getCouponIdBefore());
		buf.append(MmsTemplate.COUPONID);
		buf.append(this.getPeriodBefore());
		buf.append(MmsTemplate.START_DATE);
		buf.append("---");
		buf.append(MmsTemplate.END_DATE);
		buf.append(this.getEnding());
		return buf.toString();
	}
	public static String genCustomerContent(Coupon coupon,MarketingAct act){
		String content=act.getMmsTemplate().toString();
		content=content.replaceAll(MmsTemplate.COUPONID, coupon.getCouponId().toString());
		content=content.replaceAll(MmsTemplate.START_DATE,NewlandUtil.dataToString(act.getStartDate(), "yyyy-MM-dd"));
		content=content.replaceAll(MmsTemplate.END_DATE,NewlandUtil.dataToString(act.getEndDate(), "yyyy-MM-dd"));
		content=content.replaceAll(MmsTemplate.NAME, coupon.getAcctName());
		String account_4=coupon.getAcctNo().substring(coupon.getAcctNo().length()-4, coupon.getAcctNo().length());
		content=content.replaceAll(MmsTemplate.ACCOUNT_4, account_4);
		if(act.getBizNo().equals(Coupon.BIZ_TYPE_DISCOUNT)){
			content=content.replaceAll(MmsTemplate.VALUE, coupon.getRebateRate().multiply(new BigDecimal(10)).toString());
		}else if(act.getBizNo().equals(Coupon.BIZ_TYPE_EXCHANGE)){
			content=content.replaceAll(MmsTemplate.VALUE, coupon.getExchangeName());
		}else{
			content=content.replaceAll(MmsTemplate.VALUE,coupon.getBackAmount().toString());
		}
		return content;
		
	}
	public void  checkNum()throws AppException{
		int num=this.title1.length()+this.title2.length()+this.cardBefore.length()+this.couponIdBefore.length()+this.ending.length()+this.periodBefore.length();
		if(num>280){
			throw new AppException(ErrorsCode.BIZ_BARCODE_GEN_ERROR,"");
		}
	}
	
	
	

}
