package com.example.vusse.model.listorderadmin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ListOrderAdminModel{

	@SerializedName("data")
	private List<ListOrderAdminData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<ListOrderAdminData> data){
		this.data = data;
	}

	public List<ListOrderAdminData> getData(){
		return data;
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

	public class ListOrderAdminData{

		@SerializedName("user_id")
		private int userId;

		@SerializedName("user_name")
		private String userName;

		@SerializedName("restauran_id")
		private int restauranId;

		@SerializedName("restaurants_name")
		private String restaurantsName;

		@SerializedName("id")
		private int id;

		public void setUserName(String userName){
			this.userName = userName;
		}

		public String getUserName(){
			return userName;
		}

		public void setRestauranId(int restauranId){
			this.restauranId = restauranId;
		}

		public int getRestauranId(){
			return restauranId;
		}

		public void setUserId(int userId){
			this.userId = userId;
		}

		public int getUserId(){
			return userId;
		}

		public void setRestaurantsName(String restaurantsName){
			this.restaurantsName = restaurantsName;
		}

		public String getRestaurantsName(){
			return restaurantsName;
		}

		public void setId(int id){
			this.id = id;
		}

		public int getId(){
			return id;
		}
	}
}