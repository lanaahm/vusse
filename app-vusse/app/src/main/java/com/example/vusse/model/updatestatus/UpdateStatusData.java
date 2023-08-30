package com.example.vusse.model.updatestatus;

import com.google.gson.annotations.SerializedName;

public class UpdateStatusData{

	@SerializedName("code")
	private String code;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("restaurant_id")
	private int restaurantId;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setRestaurantId(int restaurantId){
		this.restaurantId = restaurantId;
	}

	public int getRestaurantId(){
		return restaurantId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}