package com.example.vusse.model.keranjang;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {
	private List<KeranjangModel.KeranjangModelData> data;
	private KeranjangAdapter.OnAdapterListener listener;


	public KeranjangAdapter(List<KeranjangModel.KeranjangModelData> data, KeranjangAdapter.OnAdapterListener listener){
		this.data = data;
		this.listener = listener;
	}

	@NonNull
	@Override
	public KeranjangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new KeranjangAdapter.ViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food, parent, false)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull KeranjangAdapter.ViewHolder holder, int position) {
		KeranjangModel.KeranjangModelData result = data.get(position);
		holder.nama_reston.setText(result.getNamaResto());
		holder.harga_menu.setText("Rp. "+result.getPrice());
		holder.deskripsi_menu.setText(result.getName());
		Picasso.get().load(result.getFoto())
				.fit().into(holder.foto_menu);
		holder.hapus_menu.setOnClickListener(v-> {
			listener.onClick(result);
		});
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView nama_reston, harga_menu, deskripsi_menu;
		ImageView hapus_menu, foto_menu;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			nama_reston = itemView.findViewById(R.id.nama_reston);
			harga_menu = itemView.findViewById(R.id.harga_menu);
			deskripsi_menu = itemView.findViewById(R.id.deskripsi_menu);
			hapus_menu = itemView.findViewById(R.id.hapus_menu);
			foto_menu = itemView.findViewById(R.id.foto_menu);
		}
	}

	public void setData(List<KeranjangModel.KeranjangModelData> result){
		data.clear();
		data.addAll(result);
		notifyDataSetChanged();
	}

	public interface OnAdapterListener {
		void onClick(KeranjangModel.KeranjangModelData result);
	}
}
