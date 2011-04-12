package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.newland.beecode.domain.condition.GlobalConstant;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.MarketingActSummary;

@RooJavaBean
@RooToString
@RooEntity(identifierField = "actNo", finders = { "findMarketingActsByActStatus"})
public class MarketingAct {

	/**
	 * 字典名称
	 */
	public static final String DICT_KEY_NAME = "ACT_STATUS";
	/**
	 * 业务类型字典名称
	 */
	public static final String BUSINESS_TYPE="BUSINESS_TYPE";
	/**
	 * 短信/彩信 类型字典
	 */
	public static final String MS_TYPE="MS_TYPE";
	/**
	 * 卡检查字典
	 */
	public static final String DICT_KEY_NAME_CHECK_CARD="BIND_CARD";

	/**
	 * 待审核
	 */
	public static final Integer STATUS_BEFORE_RECHECK = 0;

	/**
	 * 审核失败
	 */
	public static final Integer STATUS_RECHECK_FAIL = 1;

	/**
	 * 作废
	 */
	public static final Integer STAUS_DELETE = 2;

	/**
	 * 待发放
	 */
	public static final Integer STATUS_BEFORE_GIVE = 3;
	/**
	 * 发放中
	 */
	public static final Integer STATUS_SENDNG=4;

	/**
	 * 已发放
	 */
	public static final Integer STATUS_AFTER_GIVE = 5;

	/**
	 * 已过期
	 */
	public static final Integer STATUS_EXPIRED = 6;

	/**
	 * 已关闭
	 */
	public static final Integer STATUS_CLOSED = 7;
	/**
	 * 绑定银行卡 
	 */
	public static final String BIND_CARD_YES="1";
	/**
	 * 不绑定银行卡 
	 */
	public static final String BIND_CARD_NO="0";

	public MarketingAct() {
		amount = new BigDecimal(0);
		rebateRate = new Float(0);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "org.hibernate.id.MultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "100") })
	private Long actNo;

	@Transient
	private String actStatusDesc;

	@NotNull
	@Size(max = 30)
	private String actName;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date endDate;

	@NotNull
	@Size(max = 100)
	private String actDetail;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Date genTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date sendTime;
    
	@NotNull
	private Integer times;
	
	@Size(max = 20)
	private String checkCode;

	@NotNull
	private Float rebateRate;

	/**
	 * 兑换券价值
	 */
	private BigDecimal amount;
	
	private Integer actStatus;
	/**
	 * 礼券数量
	 */
	private Long couponSum;
	/**
	 * 彩信发放数量
	 */
	private Long mmsSendSum;
	/**
	 * 短信发放数量
	 */
	private Long smsSendSum;
	
	@Transient
	private Long sendErrSum;
	

	@NotNull
	@Size(max = 2)
	private String bizNo;
    
	@NotNull
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<Partner> partners = new HashSet<Partner>();
    
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private MarketingCatalog marketingCatalog;
	
	
	private Long operNo;
	
	@NotNull
	@Size(max = 400)
	private String mmsContent;
	
	@NotNull
	@Size(max = 40)
	private String mmsTitle;
	
	@NotNull
	@Size(max = 2)
	private String bindCard;
	@Size(max = 30)
	private String fileName;
	
	@Transient
	private String bizName;
	
	@Transient
	private MarketingActSummary summary;
	
	@Transient
	private MmsTemplate mmsTemplate;
    
	
	public String getBizName() {
		if(this.bizNo==null){
			return "";
		}
		return DictView.getDescByKeyName(BUSINESS_TYPE, this.bizNo);
	}

	public String getActStatusDesc() {
		if(this.actStatus==null){
			return "";
		}
		return DictView.getDescByKeyName(DICT_KEY_NAME,actStatus.toString());
	}
    public static QueryResult findMarketingActEntriesByActStatus(Integer actStatus,Integer page,Integer size){
    	EntityManager em = entityManager();
    	Query q = em.createQuery("from MarketingAct ma where ma.actStatus= :actStatus");
    	Query countq=em.createQuery("select count(ma) from MarketingAct ma where ma.actStatus= :actStatus");
    	countq.setParameter("actStatus", actStatus);
    	q.setParameter("actStatus", actStatus);
    	QueryResult qr=new QueryResult();
    	qr.setCount(((Long)countq.getSingleResult()).intValue());
    	qr.setResultList(q.setFirstResult((page - 1) * size).setMaxResults(size).getResultList());
    	return qr;
    }
	public static QueryResult findMarketingActsByCondition(MarketingActCondition mac) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		StringBuffer queryBuf = new StringBuffer();
		StringBuffer buf = new StringBuffer();
		StringBuffer countBuf=new StringBuffer();
		countBuf.append("SELECT count(*) FROM MarketingAct AS marketingact WHERE 1=1" );
		queryBuf.append("SELECT MarketingAct FROM MarketingAct AS marketingact WHERE 1=1  ");
		if (mac.getStartGenDate() != null && mac.getEndGenDate()!= null) {
			buf.append(" and  marketingact.genTime >= :minGenTime and marketingact.genTime <= :maxGenTime");
			conditions.put("minGenTime", mac.getStartGenDate());
			conditions.put("maxGenTime", mac.getEndGenDate());
		}
		if (mac.getBizNo() != null) {
				buf.append(" and marketingact.bizNo = :bizNo");
			conditions.put("bizNo", mac.getBizNo());
		}
		if (mac.getActName() != null && mac.getActName().length() > 0) {
			
				buf.append(" and LOWER(marketingact.actName) LIKE LOWER(:actName)");
			conditions.put("actName", "%"+mac.getActName()+"%");
		}
		if (mac.getActStatus() != null ) {
				buf.append(" and marketingact.actStatus = :actStatus");
			conditions.put("actStatus", mac.getActStatus());
		}
		if(mac.getMarketingCatalogId()!=null){
			buf.append(" and marketingact.marketingCatalog.id = :catalogId");
			conditions.put("catalogId", mac.getMarketingCatalogId());
		}
		QueryResult qr=new QueryResult();
		EntityManager em = entityManager();
		Query q = em.createQuery(countBuf.append(buf).toString());
		for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
			String cond = it.next();
			q.setParameter(cond, conditions.get(cond));
		}
		Long count=(Long)q.getSingleResult();
		qr.setCount(count.intValue());
		
		Query query=em.createQuery(queryBuf.append(buf).toString());
		for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
			String cond = it.next();
			query.setParameter(cond, conditions.get(cond));
		}
		if(mac.isPagination()){
			qr.setResultList(query.setFirstResult((mac.getPage() - 1) * mac.getSize()).setMaxResults(mac.getSize()).getResultList());
			}else{
				qr.setResultList(extracted(query));
			}
		
		return qr; 
	}

	public static void expired(Date date) {
		EntityManager em = entityManager();
		Query q = em
				.createQuery("update MarketingAct AS act set act.actStatus =:status where act.endDate<:edate");
		q.setParameter("status", STATUS_EXPIRED);
		q.setParameter("edate", date);
		q.executeUpdate();
	}

	public static MarketingActSummary marketingSummary(Long actNo) {
		Query q = entityManager().createQuery("from MarketingAct as act where act.actNo=?");
		q.setParameter(1, actNo);
		MarketingAct act = (MarketingAct)q.getSingleResult();
		
		q = entityManager()
				.createNativeQuery(
						"select sum(remain_times) remainTimes, sum(times-remain_times) usedTimes, count(marketing_act) joinCount from coupon where marketing_act=?");
		q.setParameter(1, actNo);
		Object[] obj = (Object[])q.getSingleResult();
		MarketingActSummary mas = new MarketingActSummary();
		mas.setRemainTimes(((BigDecimal)obj[0]).intValue());
		mas.setUsedTimes(((BigDecimal)obj[1]).intValue());
		mas.setJoinCount(((BigInteger)obj[2]).intValue());
		return mas;
	}
	public static List<MarketingAct> findByCatalog(Long id){
		EntityManager em = entityManager();
		Query q = em
				.createQuery("select MarketingAct from  MarketingAct marketingAct where marketingAct.marketingCatalog.id=:id");
		q.setParameter("id", id);
		return extracted(q);
	}

	private static List extracted(Query q) {
		return q.getResultList();
	}

	public MarketingActSummary getSummary() {
		return summary;
	}

	public void setSummary(MarketingActSummary summary) {
		this.summary = summary;
	}

}
