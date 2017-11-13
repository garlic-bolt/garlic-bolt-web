package com.chanjetpay.garlic.web;

import java.io.Serializable;

/**
 * Created by libaoa on 2017/11/9.
 */
public class UserDto implements Serializable{
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
