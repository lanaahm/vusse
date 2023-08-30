package com.example.vusse.model.menu;

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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private List<MenuModel.MenuData> data;
    private OnAdapterListener listener;
    public MenuAdapter(List<MenuModel.MenuData> data, MenuAdapter.OnAdapterListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail_food, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        MenuModel.MenuData result = data.get(position);
        holder.deskripsiMakanan.setText(result.getName());
        holder.harga.setText("Rp. "+result.getPrice());
        Picasso.get().load(result.getFoto())
                .fit().into(holder.fotoMakanan);
        holder.itemView.setOnClickListener(v-> {
            listener.onClick(result);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deskripsiMakanan, harga;
        ImageView fotoMakanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deskripsiMakanan = itemView.findViewById(R.id.deskripsi_makanan);
            harga = itemView.findViewById(R.id.harga);
            fotoMakanan = itemView.findViewById(R.id.foto_makanan);
        }
    }

    public void setData(List<MenuModel.MenuData> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnAdapterListener {
        void onClick(MenuModel.MenuData result);
    }
}