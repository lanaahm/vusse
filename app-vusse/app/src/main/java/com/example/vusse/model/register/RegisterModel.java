package com.example.vusse.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterModel{

	@SerializedName("data")
	private RegisterData registerData;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(RegisterData registerData){
		this.registerData = registerData;
	}

	public RegisterData getData(){
		return registerData;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}