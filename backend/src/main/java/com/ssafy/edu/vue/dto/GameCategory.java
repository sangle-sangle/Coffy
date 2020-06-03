package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class GameCategory implements Serializable {
	private int id;
	private String title;
	private String description;
	private int game_cnt;
	public GameCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameCategory(int id, String title, String description, int game_cnt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.game_cnt = game_cnt;
	}
	@Override
	public String toString() {
		return "GameCategory [id=" + id + ", title=" + title + ", description=" + description + ", game_cnt=" + game_cnt
				+ "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGame_cnt() {
		return game_cnt;
	}
	public void setGame_cnt(int game_cnt) {
		this.game_cnt = game_cnt;
	}
	
}
