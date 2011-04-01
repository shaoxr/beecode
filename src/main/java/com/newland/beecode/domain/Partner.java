package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.newland.beecode.domain.report.PartnerSummaryItem;

@RooJavaBean
@RooToString(excludeFields = { "partnerNo" })
@RooEntity
public class Partner {

	@NotNull
	@Size(max = 32)
	private String partnerNo;

	@NotNull
	@Size(max = 32)
	private String partnerName;
    
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private PartnerCatalog partnerCatalog;
	
	@Transient
	private String allName;
	
	public String getAllName(){
		return this.partnerCatalog.getCatalogName()+"-->"+this.getPartnerName();
	}
	
	public static List<PartnerSummaryItem> summaryPartner(Long id) {
		StringBuffer buf = new StringBuffer();
		buf.append("select pt.partner_name, act_name, bt.biz_name,check_day,count(*),sum(act.amount) exchange, sum(ctrl.amount) ");
		buf.append("from partner pt, marketing_act act, coupon cp, coupont_ctrl ctrl, marketing_act_partners ap , business_type bt ");
		buf.append("where pt.id=? and pt.id=ap.partners and act.act_no=ap.marketing_act and act.act_status>3 ");
		buf.append("and cp.marketing_act=act.act_no and cp.coupon_id=ctrl.coupon_id and bt.biz_no=act.biz_no ");
		buf.append("group by pt.partner_name, act_name, check_day,bt.biz_name");

		Query q = entityManager().createNativeQuery(buf.toString());
		q.setParameter(1, id);
		List l = q.getResultList();
		List<PartnerSummaryItem> items = new ArrayList<PartnerSummaryItem>();
		for (Iterator it = l.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			PartnerSummaryItem item = new PartnerSummaryItem();
			item.setPartnerName((String)obj[0]);
			item.setActName((String)obj[1]);
			item.setBizName((String)obj[2]);
			item.setCheckDay((Date)obj[3]);
			item.setCount(((BigInteger)obj[4]).intValue());
			item.setExchangeAmount((BigDecimal)obj[5]);
			item.setRebateAmount((BigDecimal)obj[6]);
			items.add(item);
		}
		return items;
	}
	public static List<Partner> findByCatalog(Long id){
		EntityManager em = entityManager();
		Query q = em
				.createQuery("select Partner from  Partner partner where partner.partnerCatalog.id=:id");
		q.setParameter("id", id);
		return q.getResultList();
	}
}
