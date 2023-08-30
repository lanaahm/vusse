package com.example.vusse.model.dalamproses;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DalamProsesModel{

	@SerializedName("data")
	private List<DalamProsesData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<DalamProsesData> data){
		this.data = data;
	}

	public List<DalamProsesData> getData(){
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
	public class DalamProsesData{

		@SerializedName("amount")
		private String amount;

		@SerializedName("foto")
		private String foto;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		public void setAmount(String amount){
			this.amount = amount;
		}

		public String getAmount(){
			return amount;
		}

		public void setFoto(String foto){
			this.foto = foto;
		}

		public String getFoto(){
			return foto;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setId(int id){
			this.id = id;
		}

		public int getId(){
			return id;
		}
	}
}