package com.example.vusse.model.keranjang;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KeranjangModel{

	@SerializedName("data")
	private List<KeranjangModelData> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<KeranjangModelData> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public class KeranjangModelData{

		@SerializedName("transactions_id")
		private int transactionsId;

		@SerializedName("foto")
		private String foto;

		@SerializedName("user_id")
		private int userId;

		@SerializedName("price")
		private int price;

		@SerializedName("name")
		private String name;

		@SerializedName("nama_resto")
		private String namaResto;

		@SerializedName("menu_id")
		private int menuId;

		public int getTransactionsId(){
			return transactionsId;
		}

		public String getFoto(){
			return foto;
		}

		public int getUserId(){
			return userId;
		}

		public int getPrice(){
			return price;
		}

		public String getName(){
			return name;
		}

		public String getNamaResto(){
			return namaResto;
		}

		public int getMenuId(){
			return menuId;
		}
	}
}