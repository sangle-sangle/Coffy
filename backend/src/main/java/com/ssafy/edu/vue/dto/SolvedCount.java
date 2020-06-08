package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class SolvedCount implements Serializable {
	private int category_id;
	private int count;

	public SolvedCount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SolvedCount(int category_id, int count) {
		super();
		this.category_id = category_id;
		this.count = count;
	}

	@Override
	public String toString() {
		return "SolvedCount [category_id=" + category_id + ", count=" + count + "]";
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
