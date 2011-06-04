package com.newland.beecode.domain.condition;

import java.util.List;
import java.util.Set;


public class CheckResult<T> {
	
	private boolean pass;
	
	private String message;
	
	private Set<T> set;
	
	private List<T> list;
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	private int count;



	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Set<T> getSet() {
		return set;
	}

	public void setSet(Set<T> set) {
		this.set = set;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
