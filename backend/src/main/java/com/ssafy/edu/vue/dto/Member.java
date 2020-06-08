package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class Member implements Serializable {
	private int id;
	private String email;
	private String username;
	private String password;
	private String githubid;
	private int clanid;
	private int point;
	private int delflag;
	private int auth;
	private String signupdate;
	private String img;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(int id, String email, String username, String password, String githubid, int clanid, int point,
			int delflag, int auth, String signupdate, String img) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.githubid = githubid;
		this.clanid = clanid;
		this.point = point;
		this.delflag = delflag;
		this.auth = auth;
		this.signupdate = signupdate;
		this.img = img;
	}
	
	public Member(int id, String email, String username, String githubid, int clanid, int point,
			int auth, String signupdate, String img) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.githubid = githubid;
		this.clanid = clanid;
		this.point = point;
		this.auth = auth;
		this.signupdate = signupdate;
		this.img = img;
	}
	
	public Member(int id, String email, String username, String password, String githubid, String signupdate) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.githubid = githubid;
		this.signupdate = signupdate;
	}
	
	public Member(int id, String email, String username, String password, String githubid, int auth, String string,
			String img) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.githubid = githubid;
		this.auth = auth;
		this.signupdate = string;
		this.img = img;
	}
	
	
	public Member(int id, String username, int clanid, int point, String img) {
		super();
		this.id = id;
		this.username = username;
		this.clanid = clanid;
		this.point = point;
		this.img = img;
	}
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", githubid=" + githubid + ", clanid=" + clanid + ", point=" + point + ", delflag=" + delflag
				+ ", auth=" + auth + ", signupdate=" + signupdate + ", img=" + img + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGithubid() {
		return githubid;
	}
	public void setGithubid(String githubid) {
		this.githubid = githubid;
	}
	public int getClanid() {
		return clanid;
	}
	public void setClanid(int clanid) {
		this.clanid = clanid;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getDelflag() {
		return delflag;
	}
	public void setDelflag(int delflag) {
		this.delflag = delflag;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public String getSignupdate() {
		return signupdate;
	}
	public void setSignupdate(String signupdate) {
		this.signupdate = signupdate;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
}
