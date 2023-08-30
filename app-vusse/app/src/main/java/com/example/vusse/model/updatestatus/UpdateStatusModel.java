package com.example.vusse.model.updatestatus;

import com.google.gson.annotations.SerializedName;

public class UpdateStatusModel{

	@SerializedName("data")
	private UpdateStatusData data;

	@SerializedName("message")
	private String message;

	public void setData(UpdateStatusData data){
		this.data = data;
	}

	public UpdateStatusData getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}