package com.example.vusse.model.detailpesananadmin;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailPesananAdminModel{

	@SerializedName("data")
	private List<DetailPesananAdminModelData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DetailPesananAdminModelData> data){
		this.data = data;
	}

	public List<DetailPesananAdminModelData> getData(){
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

	public class DetailPesananAdminModelData{

		@SerializedName("foto")
		private String foto;

		@SerializedName("price")
		private int price;

		@SerializedName("name")
		private String name;

		@SerializedName("description")
		private String description;

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
	}
}