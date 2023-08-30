package com.example.vusse.model.riwayat;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RiwayatModel {

	@SerializedName("data")
	private List<RiwayatData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<RiwayatData> data){
		this.data = data;
	}

	public List<RiwayatData> getData(){
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

	public class RiwayatData{

		@SerializedName("foto")
		private String foto;

		@SerializedName("price")
		private int price;

		@SerializedName("restaurants_name")
		private String restaurantsName;

		@SerializedName("id")
		private int id;

		@SerializedName("menus_name")
		private String menusName;

		public void setFoto(String foto){
			this.foto = foto;
		}

		public String getFoto(){
			return foto;
		}

		public void setPrice(int price){
			this.price = price;
		}

		public int getPrice(){
			return price;
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

		public void setMenusName(String menusName){
			this.menusName = menusName;
		}

		public String getMenusName(){
			return menusName;
		}
	}
}