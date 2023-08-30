package com.example.vusse.model.dalamproses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class DalamProsesAdapter extends RecyclerView.Adapter<DalamProsesAdapter.ViewHolder> {
    private List<DalamProsesModel.DalamProsesData> data;
    private OnAdapterListener listener;
    public DalamProsesAdapter(List<DalamProsesModel.DalamProsesData> data, OnAdapterListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DalamProsesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DalamProsesAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food_dalam_proses, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DalamProsesAdapter.ViewHolder holder, int position) {
        DalamProsesModel.DalamProsesData result = data.get(position);
        holder.nama_resto.setText(result.getName());
        holder.total_harga.setText("Rp. "+result.getAmount());
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
        TextView nama_resto, total_harga;
        ImageView foto_dalamproses;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_resto = itemView.findViewById(R.id.nama_resto);
            total_harga = itemView.findViewById(R.id.total_harga);
            foto_dalamproses = itemView.findViewById(R.id.foto_dalamproses);
        }
    }

    public void setData(List<DalamProsesModel.DalamProsesData> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnAdapterListener {
        void onClick(DalamProsesModel.DalamProsesData result);
    }
}