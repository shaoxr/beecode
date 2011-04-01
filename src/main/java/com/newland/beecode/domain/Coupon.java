package com.newland.beecode.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.newland.beecode.domain.condition.CouponCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.ConsumeDetail;
import com.newland.beecode.domain.report.CouponSummaryItem;
import com.newland.beecode.domain.report.PartnerSummaryItem;
import com.newland.beecode.domain.report.ReportForm;
import com.newland.beecode.domain.report.ReportResult;

@RooJavaBean
@RooToString
@RooEntity(identifierField = "couponId")
public class Coupon {
	private static final Log logger = LogFactory.getLog(Coupon.class);
	/**
	 * 字典名称
	 */
	public static final String DICT_KEY_NAME = "COUPON_STATUS";
	/**
	 * 短信彩信状态字典名称 
	 */
	public static final String MMS_SMS_STATUS_KEY_NAME="COUPON_MMS_STATUS";

	/**
	 * 有效
	 */
	public static final Integer STATUS_VALID = 1;

	/**
	 * 无效
	 */
	public static final Integer STATUS_INVALID = 0;

	/**
	 * 已用完优惠
	 */
	public static final Integer STATUS_FINISH = 4;

	/**
	 * 过期
	 */
	public static final Integer STATUS_EXPIRED = 2;

	/**
	 * 挂失
	 */
	public static final Integer STATUS_LOST = 3;
	/**
	 * 彩信等待发送
	 */
	public static final Integer MMS_STATUS_WAIT=0;
	/**
	 * 彩信已经发送
	 */
	public static final Integer MMS_STATUS_SNED=1;
	/**
	 * 彩信成功接收
	 */
	public static final Integer MMS_STATUS_REC_SUCCESS=2;
	/**
	 * 彩信接收失败
	 */
	public static final Integer MMS_STATUS_REC_ERROR=3;
	/**
	 * 彩信发送失败
	 */
	public static final Integer MMS_STATUS_SEND_ERROR=4;
	
	/**
	 * 短信等待发送
	 */
	public static final Integer SMS_STATUS_WAIT=0;
	/**
	 * 短信已经发送
	 */
	public static final Integer SMS_STATUS_SNED=1;
	/**
	 * 短信发送失败
	 */
	public static final Integer SMS_STATUS_SEND_ERROR=4;
	/**
	 * 短信成功接收
	 */
	public static final Integer SMS_STATUS_REC_SUCCESS=2;
	/**
	 * 短信接收失败
	 */
	public static final Integer SMS_STATUS_REC_ERROR=3;
	
	
	
	/**
	 * 兑换券
	 */
	public static final String BIZ_TYPE_EXCHANGE="00";
	/**
	 *  优惠券
	 */
	public static final String BIZ_TYPE_DISCOUNT="01";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGen")
	@GenericGenerator(name = "tableGen", strategy = "com.newland.utils.RandomLoMultipleHiLoPerTableGenerator", parameters = { @Parameter(name = "max_lo", value = "3000") })
	private Long couponId;

	@Size(max = 24)
	private String acctNo;

	@Size(max = 16)
	private String acctMobile;

	@Size(max = 16)
	private String checkCode;

	@Size(max = 36)
	private String serialNo;

	private Integer couponStatus;
	
	private Integer mmsStatus;
	
	@Size(max = 100)
	private String mmsDesc;
	
	@Size(max = 100)
	private String smsDesc;
	
	private Integer smsStatus;

	@Transient
	private String couponStatusDesc;

	private Float rebateRate;

	private Integer times;
	
	@Size(max = 10)
	private String acctName;
	
	@Size(max = 20)
	private String mmsId;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date genTime;

	@Size(max = 2)
	private String businessType;

	private Integer remainTimes;

	@ManyToOne
	private MarketingAct marketingAct;
	
	@Transient
	private String mmsStatusDesc;
	
	@Transient
	private String smsStatusDesc;
	
	public String getMmsStatusDesc(){
		return DictView.getDescByKeyName(MMS_SMS_STATUS_KEY_NAME, this.getMmsStatus().toString());
		
	}
	
	public String getSmsStatusDesc(){
		return DictView.getDescByKeyName(MMS_SMS_STATUS_KEY_NAME, this.getSmsStatus().toString());
	}

	public String getCouponStatusDesc() {
		return DictView.getValuesByKeyName(DICT_KEY_NAME).get(
				couponStatus.toString());
	}

	public void clearAndFlushEntityManager() {
		entityManager.flush();
		entityManager.clear();
	}
	public static List<Coupon> findByActNo(Long actNo){
		EntityManager em = entityManager();
		Query q = em.createQuery("from Coupon as coupon where coupon.marketingAct.actNo= :actNo");
		q.setParameter("actNo",actNo);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public static QueryResult findCouponsByCondition(CouponCondition cc) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		StringBuffer buf = new StringBuffer();
		StringBuffer countBuf=new StringBuffer();
		StringBuffer queryBuf=new StringBuffer();
		countBuf.append("SELECT count(coupon) FROM Coupon AS coupon ,MarketingAct as ma WHERE coupon.marketingAct.actNo=ma.actNo ");
		queryBuf.append("SELECT coupon FROM Coupon AS coupon ,MarketingAct as ma WHERE coupon.marketingAct.actNo=ma.actNo ");
		if (cc.getMinGenTime() != null && cc.getMaxGenTime() != null
				&& cc.getMinGenTime().compareTo(cc.getMaxGenTime() ) < 1) {
			buf.append(" and coupon.genTime >= :minGenTime and coupon.genTime <= :maxGenTime");
			conditions.put("minGenTime", cc.getMinGenTime());
			conditions.put("maxGenTime", cc.getMaxGenTime() );
		}
		if (cc.getCouponId() != null) {
				buf.append(" and coupon.couponId = :couponId");
			conditions.put("couponId", cc.getCouponId());
		}
		if (cc.getCouponStatus() != null ) {
				buf.append(" and coupon.couponStatus = :couponStatus");
			conditions.put("couponStatus", cc.getCouponStatus());
		}

		if (cc.getMobile() != null && cc.getMobile().length()>0) {
				buf.append(" and coupon.acctMobile = :mobile");
			conditions.put("mobile", cc.getMobile());
		}

		if (cc.getActNo() != null ) {
				buf.append(" and coupon.marketingAct.actNo = :acctNo");
			conditions.put("acctNo", cc.getActNo());
		}
		if(cc.getMarketingCatalogId()!=null){
			buf.append(" and ma.marketingCatalog.id = :id");
			conditions.put("id", cc.getMarketingCatalogId());
		}
		QueryResult qr=new QueryResult(); 
		EntityManager em = entityManager();
		String queryStr=queryBuf.append(buf).toString();
		Query q = em.createQuery(queryStr);
		for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
			String cond = it.next();
			q.setParameter(cond, conditions.get(cond));
		}
		if(cc.isPagination()){
			qr.setResultList(q.setFirstResult((cc.getPage() - 1) * cc.getSize()).setMaxResults(cc.getSize()).getResultList());
			q=em.createQuery(countBuf.append(buf).toString());
			for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
				String cond = it.next();
				q.setParameter(cond, conditions.get(cond));
			}
			qr.setCount(((Long)q.getSingleResult()).intValue());
		}else{
			qr.setResultList(q.getResultList());
		}
		return qr;
	}

	public static Coupon findBySerialNo(String serialNo) {
		Query q = entityManager()
				.createQuery(
						"select coupon from Coupon as coupon where coupon.serialNo=:serialNo");
		q.setParameter("serialNo", serialNo);
		
        List list=q.getResultList();
        if(list.size()!=0)
        	return (Coupon) list.get(0);
        return null;
        
	}
	
    @SuppressWarnings("unchecked")
	public static List<CouponSummaryItem> summaryPartnerByAct(Long actNo){
    	StringBuffer buf = new StringBuffer();
    	buf.append("select pt.partner_name, ctrl.check_day, count(*), sum(act.amount) exchange, sum(ctrl.amount) ");
    	buf.append("from coupon cp, marketing_act act, coupont_ctrl ctrl, marketing_act_partners mp, partner pt ");
    	buf.append("where cp.marketing_act=? and cp.marketing_act=act.act_no and ctrl.coupon_id=cp.coupon_id and ");
    	buf.append("mp.partners=pt.id and mp.marketing_act=act.act_no ");
    	buf.append("group by pt.partner_name, ctrl.check_day");
    	Query q = entityManager().createNativeQuery(buf.toString());
    	q.setParameter(1, actNo);
    	List l = q.getResultList();
    	List<CouponSummaryItem> result = new LinkedList<CouponSummaryItem>();
    	for (Iterator it = l.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			CouponSummaryItem cs = new CouponSummaryItem();
			cs.setPartnerName((String)obj[0]);
			cs.setDay((Date)obj[1]);
			cs.setCount(((BigInteger)obj[2]).intValue());
			cs.setExchangeAmount((BigDecimal)obj[3]);
			cs.setRebateAmount((BigDecimal)obj[4]);
			result.add(cs);
		}
    	
    	return result;
    }
    public static ReportResult reportDetail(ReportForm reportForm){
    	StringBuffer buf = new StringBuffer();
    	StringBuffer CountBuf = new StringBuffer();
    	HashMap<String, Object> conditions = new HashMap<String, Object>();
    	buf.append("select ctrl.check_day, act.act_name, pt.partner_name ,pt.partner_no,cp.coupon_id,cp.acct_mobile, " +
    			"ctrl.rebate_rate,abs(act.amount) ,bt.biz_name, (ctrl.amount*(10-ctrl.rebate_rate))/10,ctrl.amount ");
    	buf.append(" from coupont_ctrl ctrl,partner pt,coupon cp,marketing_act act,business_type bt ");
    	buf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
    	buf.append(" and bt.biz_no=act.biz_no and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");
    	CountBuf.append("select count(*) ");
    	CountBuf.append(" from coupont_ctrl ctrl,partner pt,coupon cp,marketing_act act,business_type bt ");
    	CountBuf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
    	CountBuf.append(" and bt.biz_no=act.biz_no and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");
    	
    	if(reportForm.getStartDateByString()!= null){
    		buf.append(" and ctrl.check_day >='"+reportForm.getStartDateByString()+"'");
    		CountBuf.append(" and ctrl.check_day >='"+reportForm.getStartDateByString()+"'");
    	}
    	if(reportForm.getEndDate()!= null){
    		buf.append(" and ctrl.check_day <='"+reportForm.getEndDateByString()+"'");
    		CountBuf.append(" and ctrl.check_day <='"+reportForm.getEndDateByString()+"'");
    	}
    	if(reportForm.getActNo()!=null ){
    		buf.append(" and act.act_no = '"+reportForm.getActNo()+"'" );
    		CountBuf.append(" and act.act_no = '"+reportForm.getActNo()+"'" );
    	}
    	if(reportForm.getParterNo()!=null ){
    		buf.append(" and pt.partner_no ='"+reportForm.getParterNo()+"' " );
    		CountBuf.append(" and pt.partner_no ='"+reportForm.getParterNo()+"' " );
    	}
    	if(reportForm.getPartnerCatalogId()!=null  ){
    		buf.append(" and pt.partner_catalog ='"+reportForm.getPartnerCatalogId()+"' " );
    		CountBuf.append(" and pt.partner_catalog ='"+reportForm.getPartnerCatalogId()+"' " );
    	}
    	if(reportForm.getMarketingCatalogId()!=null ){
    		buf.append(" and act.marketing_catalog = '"+reportForm.getMarketingCatalogId()+"'" );
    		CountBuf.append(" and act.marketing_catalog = '"+reportForm.getMarketingCatalogId()+"'" );
    	}
    	buf.append(" order by ctrl.check_day,act.act_name, pt.partner_name ");
    	EntityManager em = entityManager();
    	
    	Query countQ = em.createNativeQuery(CountBuf.toString());
		ReportResult rr=new ReportResult();
		BigInteger count=(BigInteger)countQ.getSingleResult();
		if(count==null){
			rr.setCount(0);
		}else{
			rr.setCount(count.longValue());
		}
		
		
		Query q = em.createNativeQuery(buf.toString());
		List list;
		if(reportForm.isPagination()){
			   list=q.setFirstResult((reportForm.getPage() - 1) * reportForm.getSize()).setMaxResults(reportForm.getSize()).getResultList();
			}else{
				list=q.getResultList();
			}
		List result=new ArrayList();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			
			ConsumeDetail cd=new ConsumeDetail();
			cd.setCheckDay((Date)obj[0]);
			cd.setActName((String)obj[1]);
			cd.setParterName((String)obj[2]);
			cd.setParterNo((String)obj[3]);
			cd.setCouponId(((BigInteger)obj[4]).toString());
			cd.setAcctMobile((String)obj[5]);
			cd.setRebateRate((BigDecimal)obj[6]);
			cd.setCost((BigDecimal)obj[7]);
			cd.setBizName((String)obj[8]);
			cd.setRebateAmount((BigDecimal)obj[9]);
			cd.setOriginalAmount((BigDecimal)obj[10]);
			//PartnerSummaryItem ps = new PartnerSummaryItem();
			//ps.setCheckDay((Date)obj[0]);
			//ps.setActName((String)obj[1]);
			//ps.setPartnerName((String)obj[2]);
			//ps.setBizName((String)obj[4]);
			//ps.setExchangeAmount((BigDecimal)obj[3]);
			//ps.setRebateAmount((BigDecimal)obj[5]);
			result.add(cd);
		}
		rr.setResultList(result);
    	return rr;
    }
    public static ReportResult reportCount(ReportForm reportForm){
    	StringBuffer buf = new StringBuffer();
    	StringBuffer countBuf = new StringBuffer();
    	
    	HashMap<String, Object> conditions = new HashMap<String, Object>();
    	buf.append("select act.act_name, pt.partner_name , count(*), sum(act.amount)  ,bt.biz_name, sum(ctrl.amount) ,sum((ctrl.amount*(10-ctrl.rebate_rate))/10) ");
    	buf.append(" from coupont_ctrl ctrl,partner pt,coupon cp,marketing_act act,business_type bt ");
    	buf.append(" where ctrl.partner_no=pt.partner_no  and ctrl.coupon_id=cp.coupon_id and  cp.marketing_act=act.act_no ");
    	buf.append(" and bt.biz_no=act.biz_no and (not ctrl.void_flag ='0' or ctrl.void_flag is null)");
    	
    	if(reportForm.getStartDateByString()!= null){
    		buf.append(" and ctrl.check_day >='"+reportForm.getStartDateByString()+"'");
    	}
    	if(reportForm.getEndDate()!= null){
    		buf.append(" and ctrl.check_day <='"+reportForm.getEndDateByString()+"'");
    	}
    	if(reportForm.getActNo()!=null ){
    		buf.append(" and act.act_no = '"+reportForm.getActNo()+"'" );
    	}
    	if(reportForm.getParterNo()!=null  ){
    		buf.append(" and pt.partner_no ='"+reportForm.getParterNo()+"' " );
    	}
    	if(reportForm.getPartnerCatalogId()!=null  ){
    		buf.append(" and pt.partner_catalog ='"+reportForm.getPartnerCatalogId()+"' " );
    	}
    	if(reportForm.getMarketingCatalogId()!=null ){
    		buf.append(" and act.marketing_catalog = '"+reportForm.getMarketingCatalogId()+"'" );
    	}
    	buf.append(" group by act.act_name, pt.partner_name ");
    	EntityManager em = entityManager();
		Query q = em.createNativeQuery(buf.toString());
		ReportResult rr=new ReportResult();
		rr.setCount(q.getResultList().size());
		List list;
		if(reportForm.isPagination()){
		   list=q.setFirstResult((reportForm.getPage() - 1) * reportForm.getSize()).setMaxResults(reportForm.getSize()).getResultList();
		}else{
			list=q.getResultList();
		}
		List result=new ArrayList();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Object[] obj = (Object[]) it.next();
			PartnerSummaryItem ps = new PartnerSummaryItem();
			ps.setActName((String)obj[0]);
			ps.setPartnerName((String)obj[1]);
			ps.setCount(((BigInteger)obj[2]).intValue());
			ps.setBizName((String)obj[4]);
			ps.setOriginalAmount((BigDecimal)obj[5]);
			ps.setExchangeAmount((BigDecimal)obj[3]);
			ps.setRebateAmount((BigDecimal)obj[6]);
			result.add(ps);
		}
		rr.setResultList(result);
    	return rr;
    }
}
