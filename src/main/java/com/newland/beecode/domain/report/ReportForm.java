package com.newland.beecode.domain.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newland.beecode.domain.condition.GlobalConstant;

public class ReportForm {
	
	private Date startDate;
	
	private Date endDate;
	
	private String marketName;
	
	private String parterName;
	
	private String actNo;
	
	private Long partnerCatalogId;
	
	private Long marketingCatalogId;
	

	private String parterNo;
	
	private boolean pagination;
	
	public Long getPartnerCatalogId() {
		if(GlobalConstant.ALL_LONG.equals(this.partnerCatalogId)){
			return null;
		}
		return partnerCatalogId;
	}

	public void setPartnerCatalogId(Long partnerCatalogId) {
		this.partnerCatalogId = partnerCatalogId;
	}

	public Long getMarketingCatalogId() {
		if(GlobalConstant.ALL_LONG.equals(this.marketingCatalogId)){
			return null;
		}
		return marketingCatalogId;
	}

	public void setMarketingCatalogId(Long marketingCatalogId) {
		this.marketingCatalogId = marketingCatalogId;
	}
	
	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public String getActNo() {
		if(GlobalConstant.ALL_STRING.equals(this.actNo)){
			return null;
		}
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getParterNo() {
		if(GlobalConstant.ALL_STRING.equals(this.parterNo)){
			return null;
		}
		return parterNo;
	}

	public void setParterNo(String parterNo) {
		this.parterNo = parterNo;
	}

	private Integer page;
	
	private Integer size;
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dateStr = sdf.format(new Date());

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
    public String getEndDateByString(){
    	if(endDate==null)
    		return null;
    	return sdf.format(endDate);
    }
    public String getStartDateByString(){
    	if(startDate==null)
    		return null;
    	return sdf.format(startDate); 
    }
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getParterName() {
		return parterName;
	}

	public void setParterName(String parterName) {
		this.parterName = parterName;
	}

}
