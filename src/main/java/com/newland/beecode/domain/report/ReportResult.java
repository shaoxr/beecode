package com.newland.beecode.domain.report;

import java.util.List;

public class ReportResult {
	
	private List resultList;
	
	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	private long count;

}
