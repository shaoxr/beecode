package com.newland.beecode.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.report.PartnerSummaryItem;
import com.newland.beecode.domain.report.PartnerSummaryReport;
import com.newland.beecode.service.PartnerService;

@Service("partnerService")
public class PartnerServiceImpl implements PartnerService {

	@Override
	public PartnerSummaryReport summaryReport(Long id) {
		List<PartnerSummaryItem> items = Partner.summaryPartner(id);
		PartnerSummaryReport rpt = new PartnerSummaryReport();
		rpt.setItems(items);
		
		Map<String, PartnerSummaryReport.DayReport> temp = new HashMap<String, PartnerSummaryReport.DayReport>();
		for (Iterator it = items.iterator(); it.hasNext();) {
			PartnerSummaryItem item = (PartnerSummaryItem) it.next();
			PartnerSummaryReport.DayReport day = temp.get(item.getCheckDay()+item.getBizName());
			if(day == null){
				day = new PartnerSummaryReport.DayReport();
				day.setDay(item.getCheckDay());
				day.setDayCount(item.getCount());
				day.setBizName(item.getBizName());
				day.setExchangeAmount(item.getExchangeAmount());
				day.setRebateAmount(item.getRebateAmount());
				temp.put(item.getCheckDay()+item.getBizName(), day);
			} else{
				day.setDayCount(day.getDayCount() + item.getCount());
				day.setExchangeAmount(day.getExchangeAmount().add(item.getExchangeAmount()));
				day.setRebateAmount(day.getRebateAmount().add(item.getRebateAmount()));
			}
		}
		rpt.setDayRpts(new ArrayList<PartnerSummaryReport.DayReport>());
		rpt.getDayRpts().addAll(temp.values());
		
		Map<String, PartnerSummaryReport.MarketingReport> pmp = new HashMap<String, PartnerSummaryReport.MarketingReport>();
		for (Iterator it = items.iterator(); it.hasNext();) {
			PartnerSummaryItem item = (PartnerSummaryItem) it.next();
			PartnerSummaryReport.MarketingReport mr = pmp.get(item.getActName());
			if(mr == null){
				mr = new PartnerSummaryReport.MarketingReport();
				mr.setActName(item.getActName());
				mr.setBizName(item.getBizName());
				mr.setUsedTimes(item.getCount());
				mr.setExchangeAmount(item.getExchangeAmount());
				mr.setRebateAmount(item.getRebateAmount());
				pmp.put(item.getActName(), mr);
			} else{
				mr.setUsedTimes(mr.getUsedTimes() + item.getCount());
				mr.setExchangeAmount(mr.getExchangeAmount().add(item.getExchangeAmount()));
				mr.setRebateAmount(mr.getRebateAmount().add(item.getRebateAmount()));
			}
		}
		rpt.setMarketingRpts(new ArrayList<PartnerSummaryReport.MarketingReport>());
		rpt.getMarketingRpts().addAll(pmp.values());
		
		return rpt;
	}

}
