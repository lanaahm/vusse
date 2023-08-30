package com.example.vusse.model.login;

import com.google.gson.annotations.SerializedName;

public class Logout{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}