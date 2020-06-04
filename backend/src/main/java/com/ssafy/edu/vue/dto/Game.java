package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class Game implements Serializable{
	private int id;
	private String title;
	private int category;
	private String base;
	private String problem;
	private String description;
	private String hint;
	private int item_cnt;
	private String text;
	private String after;
	public Game() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Game(int id, String title, int category, String base, String problem, String description, String hint,
			int item_cnt, String text, String after) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.base = base;
		this.problem = problem;
		this.description = description;
		this.hint = hint;
		this.item_cnt = item_cnt;
		this.text = text;
		this.after = after;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", category=" + category + ", base=" + base + ", problem="
				+ problem + ", description=" + description + ", hint=" + hint + ", item_cnt=" + item_cnt + ", text="
				+ text + ", after=" + after + "]";
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
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public int getItem_cnt() {
		return item_cnt;
	}
	public void setItem_cnt(int item_cnt) {
		this.item_cnt = item_cnt;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	
}
