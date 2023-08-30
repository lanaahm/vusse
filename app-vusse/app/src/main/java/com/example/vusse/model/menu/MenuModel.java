package com.example.vusse.model.menu;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuModel{

	@SerializedName("data")
	private List<MenuData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<MenuData> data){
		this.data = data;
	}

	public List<MenuData> getData(){
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

	public class MenuData {

		@SerializedName("foto")
		private String foto;

		@SerializedName("price")
		private int price;

		@SerializedName("name")
		private String name;

		@SerializedName("description")
		private String description;

		@SerializedName("id")
		private int id;

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

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setDescription(String description){
			this.description = description;
		}

		public String getDescription(){
			return description;
		}

		public void setId(int id){
			this.id = id;
		}

		public int getId(){
			return id;
		}
	}
}