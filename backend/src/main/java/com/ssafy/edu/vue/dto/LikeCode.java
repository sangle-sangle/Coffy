package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class LikeCode implements Serializable  {
	private int codeid;
	private int userid;
	public LikeCode() {
		super();
		
	}
	public LikeCode(int codeid, int userid) {
		super();
		this.codeid = codeid;
		this.userid = userid;
	}
	public int getCodeid() {
		return codeid;
	}
	public void setCodeid(int codeid) {
		this.codeid = codeid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "LikeCode [codeid=" + codeid + ", userid=" + userid + "]";
	}
	
}
