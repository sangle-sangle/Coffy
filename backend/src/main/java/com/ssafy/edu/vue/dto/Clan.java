package com.ssafy.edu.vue.dto;

import org.springframework.data.annotation.Id;

public class Clan {
	@Id
	private int id;
	private String name;
	private String description;
	private int leaderId;
	private int locked; // 0: 공개, 1: 비공개 
	private String password;
	private String clanmark;
	private String clanmarkdeletehash;
	private String created_at;
	private String mastername;
	private int clanpoint;
	
	public Clan() {
		super();
		// TODO Auto-generated constructor stub
	}

		
	public Clan(int id, String name, String description, int leaderId, int locked, String password, String clanmark, String clanmarkdeletehash, String created_at, String mastername, int clanpoint) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.leaderId = leaderId;
		this.locked = locked;
		this.password = password;
		this.clanmark = clanmark;
		this.clanmarkdeletehash = clanmarkdeletehash;
		this.created_at = created_at;
		this.mastername = mastername;
		this.clanpoint = clanpoint;
	}
	
	public Clan(int id, String name, String description, int leaderId, int locked, String password, String clanmark, String clanmarkdeletehash, String created_at, int clanpoint) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.leaderId = leaderId;
		this.locked = locked;
		this.password = password;
		this.clanmark = clanmark;
		this.clanmarkdeletehash = clanmarkdeletehash;
		this.created_at = created_at;
		this.clanpoint = clanpoint;
	}
	
	public Clan(int id, String name, String description, int locked, String password, String clanmark, String clanmarkdeletehash) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.locked = locked;
		this.password = password;
		this.clanmark = clanmark;
		this.clanmarkdeletehash = clanmarkdeletehash;
	}

	public Clan(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Clan [id=" + id + ", name=" + name + ", description=" + description + ", leaderId=" + leaderId
				+ ", locked=" + locked + ", password=" + password + ", clanmark=" + clanmark + ", clanmarkdeletehash="
				+ clanmarkdeletehash + ", created_at=" + created_at + ", mastername=" + mastername + ", clanpoint="
				+ clanpoint + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getLeaderId() {
		return leaderId;
	}


	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}


	public int getLocked() {
		return locked;
	}


	public void setLocked(int locked) {
		this.locked = locked;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getCreated_at() {
		return created_at;
	}


	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getClanmark() {
		return clanmark;
	}

	public void setClanmark(String clanmark) {
		this.clanmark = clanmark;
	}


	public String getMastername() {
		return mastername;
	}


	public void setMastername(String mastername) {
		this.mastername = mastername;
	}


	public String getClanmarkdeletehash() {
		return clanmarkdeletehash;
	}


	public void setClanmarkdeletehash(String clanmarkdeletehash) {
		this.clanmarkdeletehash = clanmarkdeletehash;
	}

	
	public int getClanpoint() {
		return clanpoint;
	}


	public void setClanpoint(int clanpoint) {
		this.clanpoint = clanpoint;
	}
}
