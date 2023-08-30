package com.example.vusse.model.listorderadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.R;

import java.util.List;

public class ListOrderAdminAdapter extends RecyclerView.Adapter<ListOrderAdminAdapter.ViewHolder> {
    private List<ListOrderAdminModel.ListOrderAdminData> data;
    private OnAdapterListener listener;
    public ListOrderAdminAdapter(List<ListOrderAdminModel.ListOrderAdminData> data, OnAdapterListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListOrderAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListOrderAdminAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderAdminAdapter.ViewHolder holder, int position) {
        ListOrderAdminModel.ListOrderAdminData result = data.get(position);
        holder.nama_user.setText(result.getUserName());
        holder.nama_resto.setText(result.getRestaurantsName());
        holder.itemView.setOnClickListener(v-> {
            listener.onClick(result);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_user, nama_resto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_user = itemView.findViewById(R.id.nama_user);
            nama_resto = itemView.findViewById(R.id.nama_resto);
        }
    }

    public void setData(List<ListOrderAdminModel.ListOrderAdminData> result){
        data.clear();
        data.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnAdapterListener {
        void onClick(ListOrderAdminModel.ListOrderAdminData result);
    }
}