package com.ssafy.edu.vue.dto;

import java.io.Serializable;

public class CheckSignUp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5623309535604336010L;

	private boolean signup;
	private String message;
	
	public CheckSignUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckSignUp(boolean signup, String message) {
		super();
		this.signup = signup;
		this.message = message;
	}

	public boolean isSignup() {
		return signup;
	}

	public void setSignup(boolean signup) {
		this.signup = signup;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CheckSignUp [signup=" + signup + ", message=" + message + "]";
	}
	
	
	

}
