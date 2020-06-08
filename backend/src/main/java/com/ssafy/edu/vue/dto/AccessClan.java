package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class AccessClan implements Serializable{

	private int user_id;
	private int clan_id;
	private String pwd;
	
	public AccessClan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessClan(int user_id, int clan_id, String pwd) {
		super();
		this.user_id = user_id;
		this.clan_id = clan_id;
		this.pwd = pwd;
	}
	
	public AccessClan(int user_id, int clan_id) {
		super();
		this.user_id = user_id;
		this.clan_id = clan_id;
	}
	
	@Override
	public String toString() {
		return "AccessClan [user_id=" + user_id + ", clan_id=" + clan_id + ", pwd=" + pwd + "]";
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getClan_id() {
		return clan_id;
	}

	public void setClan_id(int clan_id) {
		this.clan_id = clan_id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
