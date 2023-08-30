package com.example.vusse.model.riwayat;

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

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {
    private List<RiwayatModel.RiwayatData> data;
    private OnAdapterListener listener;


    public RiwayatAdapter(List<RiwayatModel.RiwayatData> data, RiwayatAdapter.OnAdapterListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RiwayatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiwayatAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food_riwayat, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull RiwayatAdapter.ViewHolder holder, int position) {
        RiwayatModel.RiwayatData result = data.get(position);
        holder.nama_reston.setText(result.getRestaurantsName());
        holder.harga_menu.setText("Rp. "+result.getPrice());
        holder.deskripsi_menu.setText(result.getMenusName());
        Picasso.get().load(result.getFoto())
                .fit().into(holder.foto_menu);
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

    public void setData(List<RiwayatModel.RiwayatData> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnAdapterListener {
        void onClick(RiwayatModel.RiwayatData result);
    }
}