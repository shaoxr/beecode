package com.newland.beecode.domain.report;

import java.math.BigDecimal;


public class MarketingActSummary{

	/**
	 * 已用次数
	 */
	private Integer usedTimes;
	
	/**
	 * 剩余次数
	 */
	private Integer remainTimes;
	
	/**
	 * 活动人数
	 */
	private Integer joinCount;

	public Integer getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Integer getRemainTimes() {
		return remainTimes;
	}

	public void setRemainTimes(Integer remainTimes) {
		this.remainTimes = remainTimes;
	}

	public Integer getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}
	
}
