package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class Solved implements Serializable {
	private int category_id;
	private int game_id;
	private int user_id;
	public Solved() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Solved(int category_id, int game_id, int user_id) {
		super();
		this.category_id = category_id;
		this.game_id = game_id;
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Solved [category_id=" + category_id + ", game_id=" + game_id + ", user_id=" + user_id + "]";
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public int getGame_id() {
		return game_id;
	}
	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
