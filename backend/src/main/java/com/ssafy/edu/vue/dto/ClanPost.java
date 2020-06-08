package com.ssafy.edu.vue.dto;

public class ClanPost {
	private int postid;
	private int clanid;
	private String title;
	private String content;
	private String created_at;
	private int writerid;
	private String writername;
	public ClanPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClanPost(int postid, int clanid, String title, String content, String created_at, int writerid) {
		super();
		this.postid = postid;
		this.clanid = clanid;
		this.title = title;
		this.content = content;
		this.created_at = created_at;
		this.writerid = writerid;
	}
	
	public ClanPost(int postid, int clanid, String title, String content, String created_at, int writerid,
			String writername) {
		super();
		this.postid = postid;
		this.clanid = clanid;
		this.title = title;
		this.content = content;
		this.created_at = created_at;
		this.writerid = writerid;
		this.writername = writername;
	}
	@Override
	public String toString() {
		return "ClanPost [postid=" + postid + ", clanid=" + clanid + ", title=" + title + ", content=" + content
				+ ", created_at=" + created_at + ", writerid=" + writerid + "]";
	}
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public int getClanid() {
		return clanid;
	}
	public void setClanid(int clanid) {
		this.clanid = clanid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public int getWriterid() {
		return writerid;
	}
	public void setWriterid(int writerid) {
		this.writerid = writerid;
	}
	public String getWritername() {
		return writername;
	}
	public void setWritername(String writername) {
		this.writername = writername;
	}
	
}
