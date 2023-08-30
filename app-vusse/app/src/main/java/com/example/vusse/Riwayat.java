package com.example.vusse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.dalamproses.DalamProsesAdapter;
import com.example.vusse.model.dalamproses.DalamProsesModel;
import com.example.vusse.model.riwayat.RiwayatAdapter;
import com.example.vusse.model.riwayat.RiwayatModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Riwayat extends Fragment {
    ImageButton btnCart;
    TextView btnDalamProses;

    SessionManager sessionManager;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    RiwayatAdapter riwayatAdapter;
    private List<RiwayatModel.RiwayatData> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.riwayat, container, false);
        sessionManager = new SessionManager(container.getContext());

        btnDalamProses = view.findViewById(R.id.dalam_proses);
        btnDalamProses.setOnClickListener(v -> {
            loadFragment(new DalamProses());
        });

        setupRecycle(view);
        setupRecycleResto();
        getDataRestoFromApi(sessionManager.getUserDetail().get("user_id"), sessionManager.getUserDetail().get("token"));

        btnCart = view.findViewById(R.id.cart);
        btnCart.setOnClickListener(v -> {
            loadFragment(new Beranda());
        });

        return view;
    }

    private void setupRecycle(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setupRecycleResto() {
        riwayatAdapter = new RiwayatAdapter(data, new RiwayatAdapter.OnAdapterListener() {
            @Override
            public void onClick(RiwayatModel.RiwayatData result) {
                Toast.makeText(getContext(), result.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(riwayatAdapter);
    }

    private void getDataRestoFromApi(String user_id, String token){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RiwayatModel> callDalamProses = apiInterface.riwayatResponse(user_id,"Bearer "+token);
        callDalamProses.enqueue(new Callback<RiwayatModel>() {
            @Override
            public void onResponse(Call<RiwayatModel> call, Response<RiwayatModel> response) {
                if (response.isSuccessful()) {
                    List<RiwayatModel.RiwayatData> data = response.body().getData();
                    riwayatAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<RiwayatModel> call, Throwable t) {
                Log.d("Fail", t.getLocalizedMessage());
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
