package com.newland.notesystem;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newland.message.MessageException;
import com.newland.posp.Message;

public class NoteMessage extends Message{

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass().getName());

	private boolean succeed;

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public String getSrvid() {
		return this.getString(0);
	}

	public void setSrvid(String srvid) {
		try {
			this.setString(0, srvid);
		} catch (MessageException e) {
			log.error(e);
		}
	}
	public String getSrvtype() {
		return this.getString(1);
	}
	public void setSrvtype(String srvtype) {
		try {
			this.setString(1, srvtype);
		} catch (MessageException e) {
			log.error(e);
		}
	}	
	public String getSrvaccno() {
		return this.getString(2);
	}

	public void setSrvaccno(String srvaccno) {
		try {
			this.setString(2, srvaccno);
		} catch (MessageException e) {
			log.error(e);
		}
	}	
	public String getObjaddr() {
		return this.getString(3);
	}

	public void setObjaddr(String objaddr) {
		try {
			this.setString(3, objaddr);
		} catch (MessageException e) {
			log.error(e);
		}
	}	
	public String getAgentno() {
		return this.getString(4);
	}

	public void setAgentno(String agentno) {
		try {
			this.setString(4, agentno);
		} catch (MessageException e) {
			log.error(e);
		}
	}	
	public String getMsgcont() {
		return this.getString(5);
	}

	public void setMsgcont(String msgcont) {
		try {
			this.setString(5, msgcont);
		} catch (MessageException e) {
			log.error(e);
		}
	}	
	public Date getCrtdate() {
		return (Date)getField(6).getValue();
	}
	public void setCrtdate(Date crtdate) {
		try {
			this.setDate(6, crtdate);
		} catch (MessageException e) {
			log.error(e);
		}
	}
	public Date getCrttime() {
		return (Date)getField(7).getValue();
	}

	public void setCrttime(Date crttime) {
		try {
			this.setDate(7, crttime);
		} catch (MessageException e) {
			log.error(e);
		}
	}
	
	public String getReserve() {
		return this.getString(8);
	}

	public void setReserve(String reserve) {
		try {
			this.setString(8, reserve);
		} catch (MessageException e) {
			log.error(e);
		}
	}


	



}
