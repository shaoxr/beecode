package com.newland.beecode.domain;
/**
 * @author shaoxr:
 * @version 2011-5-14 下午04:38:58
 * 
 */
public class MsStatus {
	
	private long MMSSuccessCount;
	
	private long SMSSuccessCount;
	
	private long MMSFailCount;
	
	private long SMSFailCount;
	
	private long totalCount;

	public long getMMSSuccessCount() {
		return MMSSuccessCount;
	}

	public void setMMSSuccessCount(long mMSSuccessCount) {
		MMSSuccessCount = mMSSuccessCount;
	}

	public long getSMSSuccessCount() {
		return SMSSuccessCount;
	}

	public void setSMSSuccessCount(long sMSSuccessCount) {
		SMSSuccessCount = sMSSuccessCount;
	}

	public long getMMSFailCount() {
		return this.totalCount-this.MMSSuccessCount;
	}

	public void setMMSFailCount(long mMSFailCount) {
		MMSFailCount = mMSFailCount;
	}

	public long getSMSFailCount() {
		return this.totalCount-this.SMSSuccessCount;
	}

	public void setSMSFailCount(long sMSFailCount) {
		SMSFailCount = sMSFailCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
