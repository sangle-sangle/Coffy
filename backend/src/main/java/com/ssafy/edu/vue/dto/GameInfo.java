package com.ssafy.edu.vue.dto;

public class GameInfo {
	private int category;
	private int id;
	public GameInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameInfo(int category, int id) {
		super();
		this.category = category;
		this.id = id;
	}
	@Override
	public String toString() {
		return "GameInfo [category=" + category + ", id=" + id + "]";
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
