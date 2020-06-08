package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class CodeComment implements Serializable {
	private int id;
	private int codeid;
	private int userid;
	private String created_at;
	private String content;
	private String writername;

	public CodeComment() {
		super();
	}
	public CodeComment(int id, int codeid, int userid, String created_at, String content) {
		super();
		this.id = id;
		this.codeid = codeid;
		this.userid = userid;
		this.created_at = created_at;
		this.content = content;
	}
	
	public CodeComment(int id, int codeid, int userid, String created_at, String content, String writername) {
		super();
		this.id = id;
		this.codeid = codeid;
		this.userid = userid;
		this.created_at = created_at;
		this.content = content;
		this.writername = writername;
	}
	@Override
	public String toString() {
		return "CodeComment [id=" + id + ", codeid=" + codeid + ", userid=" + userid + ", created_at=" + created_at
				+ ", content=" + content + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWritername() {
		return writername;
	}
	public void setWritername(String writername) {
		this.writername = writername;
	}
	
}
