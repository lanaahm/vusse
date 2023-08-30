package com.example.vusse.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginModel{

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private LoginData loginData;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setUser(LoginData loginData){
		this.loginData = loginData;
	}

	public LoginData getUser(){
		return loginData;
	}

}