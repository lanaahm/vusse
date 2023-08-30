package com.example.vusse.model.restoran;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestoranAdapter extends RecyclerView.Adapter<RestoranAdapter.ViewHolder> {
    private List<RestoranModel.RestoranData> data;
    private OnAdapterListener listener;
    public RestoranAdapter(List<RestoranModel.RestoranData> data, OnAdapterListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestoranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_resto, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranAdapter.ViewHolder holder, int position) {
        RestoranModel.RestoranData result = data.get(position);
        holder.namaRestoran.setText(result.getName());
        holder.ratingRestoran.setText(result.getRating().toString());
        Picasso.get().load(result.getFoto())
                .fit().into(holder.foto_resto);
        holder.itemView.setOnClickListener(v-> {
                listener.onClick(result);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaRestoran, ratingRestoran;
        ImageView foto_resto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaRestoran = itemView.findViewById(R.id.nama_resto);
            ratingRestoran = itemView.findViewById(R.id.rating);
            foto_resto = itemView.findViewById(R.id.foto_resto);
        }
    }

    public void setData(List<RestoranModel.RestoranData> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnAdapterListener {
        void onClick(RestoranModel.RestoranData result);
    }
}
