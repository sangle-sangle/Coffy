package com.ssafy.edu.vue.dto;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
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
	
	public Clan() {
		super();
		// TODO Auto-generated constructor stub
	}

		
	public Clan(int id, String name, String description, int leaderId, int locked, String password, String clanmark, String clanmarkdeletehash, String created_at) {
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
	}


	@Override
	public String toString() {
		return "Clan [id=" + id + ", name=" + name + ", description=" + description + ", leaderId=" + leaderId
				+ ", locked=" + locked + ", password=" + password + ", clanmark=" + clanmark
				+ ", clanmarkdeletehash=" + clanmarkdeletehash + ", created_at=" + created_at + "]";
	}
		
	
}

