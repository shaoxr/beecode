package com.newland.beecode.domain;

public class MmsTemplate {
	public static final String NAME="%#name#%";
	
	public static final String START_DATE="%#start_date#%";
	
	public static final String END_DATE="%#end_date#%";
	
	public static final String COUPONID="%#couponid#%";
	
	public String getPart1() {
		return part1;
	}

	public void setPart1(String part1) {
		this.part1 = part1;
	}

	public String getPart2() {
		return part2;
	}

	public void setPart2(String part2) {
		this.part2 = part2;
	}

	public String getPart3() {
		return part3;
	}

	public void setPart3(String part3) {
		this.part3 = part3;
	}

	public String getPart4() {
		return part4;
	}

	public void setPart4(String part4) {
		this.part4 = part4;
	}

	public String getPart5() {
		return part5;
	}

	public void setPart5(String part5) {
		this.part5 = part5;
	}

	public String getPart6() {
		return part6;
	}

	public void setPart6(String part6) {
		this.part6 = part6;
	}

	public static String getName() {
		return NAME;
	}

	public static String getStartDate() {
		return START_DATE;
	}

	public static String getEndDate() {
		return END_DATE;
	}

	private String part1;
	
	private String part2;
	
	private String part3;
	
	private String part4;
	
	private String part5;
	
	private String part6;
	
	public String getTemplateContent(){
		StringBuffer buf=new StringBuffer();
		buf.append(this.getPart1());
		buf.append(MmsTemplate.NAME);
		buf.append(this.getPart2());
		buf.append(this.getPart3());
		buf.append(MmsTemplate.COUPONID);
		buf.append(this.getPart4());
		buf.append(MmsTemplate.START_DATE);
		buf.append("-----");
		buf.append(MmsTemplate.END_DATE);
		buf.append(this.getPart5());
		return buf.toString();
	}
	
	
	

}
