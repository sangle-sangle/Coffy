package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class CommentInfo implements Serializable {
	private int codeid;
	private int memberid;
	public CommentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentInfo(int codeid, int memberid) {
		super();
		this.codeid = codeid;
		this.memberid = memberid;
	}
	@Override
	public String toString() {
		return "CommentInfo [codeid=" + codeid + ", memberid=" + memberid + "]";
	}
	public int getCodeid() {
		return codeid;
	}
	public void setCodeid(int codeid) {
		this.codeid = codeid;
	}
	public int getMemberid() {
		return memberid;
	}
	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}
	
}
