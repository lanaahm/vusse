package com.example.vusse.model.restoran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RestoranModel{

	@SerializedName("data")
	private List<RestoranData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setData(List<RestoranData> data){
		this.data = data;
	}

	public List<RestoranData> getData(){
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

	public class RestoranData {

		@SerializedName("foto")
		private String foto;

		@SerializedName("latitude")
		private String latitude;

		@SerializedName("name")
		private String name;

		@SerializedName("rating")
		private Object rating;

		@SerializedName("id")
		private int id;

		@SerializedName("longitude")
		private String longitude;

		public void setFoto(String foto){
			this.foto = foto;
		}

		public String getFoto(){
			return foto;
		}

		public void setLatitude(String latitude){
			this.latitude = latitude;
		}

		public String getLatitude(){
			return latitude;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getName(){
			return name;
		}

		public void setRating(Object rating){
			this.rating = rating;
		}

		public Object getRating(){
			return rating;
		}

		public void setId(int id){
			this.id = id;
		}

		public int getId(){
			return id;
		}

		public void setLongitude(String longitude){
			this.longitude = longitude;
		}

		public String getLongitude(){
			return longitude;
		}
	}

}