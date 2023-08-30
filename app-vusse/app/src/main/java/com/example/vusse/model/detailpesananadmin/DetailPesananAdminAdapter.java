package com.example.vusse.model.detailpesananadmin;

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

public class DetailPesananAdminAdapter extends RecyclerView.Adapter<DetailPesananAdminAdapter.ViewHolder> {
    private List<DetailPesananAdminModel.DetailPesananAdminModelData> data;
    private OnAdapterListener listener;
    public DetailPesananAdminAdapter(List<DetailPesananAdminModel.DetailPesananAdminModelData> data, DetailPesananAdminAdapter.OnAdapterListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetailPesananAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailPesananAdminAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail_user, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPesananAdminAdapter.ViewHolder holder, int position) {
        DetailPesananAdminModel.DetailPesananAdminModelData result = data.get(position);
        holder.total_harga.setText(String.valueOf(result.getPrice()));
        holder.deskripsi_menu.setText(result.getDescription());
        Picasso.get().load(result.getFoto())
                .fit().into(holder.foto_dalamproses);
        holder.itemView.setOnClickListener(v-> {
            listener.onClick(result);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total_harga, deskripsi_menu;
        ImageView foto_dalamproses;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            total_harga = itemView.findViewById(R.id.total_harga);
            deskripsi_menu = itemView.findViewById(R.id.deskripsi_menu);
            foto_dalamproses = itemView.findViewById(R.id.foto_dalamproses);
        }
    }

    public void setData(List<DetailPesananAdminModel.DetailPesananAdminModelData> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnAdapterListener {
        void onClick(DetailPesananAdminModel.DetailPesananAdminModelData result);
    }
}