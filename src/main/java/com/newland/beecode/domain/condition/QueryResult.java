package com.newland.beecode.domain.condition;

import java.util.List;

public class QueryResult {
	
	private List resultList;
	
	private int count;

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
