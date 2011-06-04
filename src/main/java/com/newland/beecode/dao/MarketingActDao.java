/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newland.beecode.dao;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.report.MarketingActSummary;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author skylai
 */
@Repository("marketingActDao")
public class MarketingActDao extends BaseDao<MarketingAct, Long> {


	public MarketingActSummary marketingSummary(Long actNo) {
		String sql = "select sum(remain_times) remainTimes, sum(times-remain_times) usedTimes, count(marketing_act) joinCount from coupon where marketing_act="
				+ actNo;
		List<Object[]> list = this.findBySql(sql);
		if (!list.isEmpty()) {
			Object[] obj = (Object[]) list.get(0);
			MarketingActSummary mas = new MarketingActSummary();
			mas.setRemainTimes(((BigDecimal) obj[0]).intValue());
			mas.setUsedTimes(((BigDecimal) obj[1]).intValue());
			mas.setJoinCount(((BigInteger) obj[2]).intValue());
			return mas;
		}
		return null;
	}
}
