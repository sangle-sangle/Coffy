package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class Code implements Serializable {
	private int id;
	private String title;
	private String description;
	private String html;
	private String css;
	private String javascript;
	private int writerid;
	private String created_at;
	private int likes;
	private String writername;
	

	public Code() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Code(int id, String title, String description, String html, String css, String javascript, int writerid,
			String created_at, int likes, String writername) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.html = html;
		this.css = css;
		this.javascript = javascript;
		this.writerid = writerid;
		this.created_at = created_at;
		this.likes = likes;
		this.writername = writername;
	}
	
	@Override
	public String toString() {
		return "Code [id=" + id + ", title=" + title + ", description=" + description + ", html=" + html + ", css="
				+ css + ", javascript=" + javascript + ", writerid=" + writerid + ", created_at=" + created_at + ","
						+ " likes=" + likes + ", writername=" + writername + "]";
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

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public int getWriterid() {
		return writerid;
	}

	public void setWriterid(int writerid) {
		this.writerid = writerid;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getWritername() {
		return writername;
	}

	public void setWritername(String writername) {
		this.writername = writername;
	}
	
}