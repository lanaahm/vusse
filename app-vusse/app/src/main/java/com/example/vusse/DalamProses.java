package com.example.vusse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.dalamproses.DalamProsesAdapter;
import com.example.vusse.model.dalamproses.DalamProsesModel;
import com.example.vusse.model.keranjang.KeranjangAdapter;
import com.example.vusse.model.keranjang.KeranjangModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DalamProses extends Fragment {
    SessionManager sessionManager;
    ImageButton btnCart;
    TextView btnRiwayat;
    AppCompatButton porder;

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    KeranjangAdapter keranjangAdapter;
    private List<KeranjangModel.KeranjangModelData> data = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dalam_proses, container, false);
        sessionManager = new SessionManager(container.getContext());

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Spinner dropdown = view.findViewById(R.id.dropdown_menu);
        String[] items = new String[]{"11-12 JAM", "13-15 JAM", "16-18 JAM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        btnCart = view.findViewById(R.id.cart);
        btnCart.setOnClickListener(v -> {
            loadFragment(new Beranda());
        });

        porder = view.findViewById(R.id.porder);
        porder.setOnClickListener(v->{
            loadFragment(new PaymentQR  ());
        });
        setupRecycle(view);
        setupRecycleMenu();
        getDataMenuFromApi(sessionManager.getUserDetail().get("user_id"), sessionManager.getUserDetail().get("token"));

        btnRiwayat = view.findViewById(R.id.riwayat);
        btnRiwayat.setOnClickListener(v -> {
            loadFragment(new Riwayat());
        });
        return view;
    }

    private void setupRecycle(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setupRecycleMenu() {
        keranjangAdapter = new KeranjangAdapter(data, new KeranjangAdapter.OnAdapterListener() {
            @Override
            public void onClick(KeranjangModel.KeranjangModelData result) {
                Call<KeranjangModel> callHapus = apiInterface.keranjangHapus(
                        result.getTransactionsId(),
                        result.getUserId(),
                        result.getMenuId(),
                        "Bearer "+sessionManager.getUserDetail().get("token"));
                callHapus.enqueue(new Callback<KeranjangModel>() {
                    @Override
                    public void onResponse(Call<KeranjangModel> call, Response<KeranjangModel> response) {
                        if (response.isSuccessful()) {
                            String data = response.body().getMessage();
                            Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                            loadFragment(new DalamProses());
                        }
                    }

                    @Override
                    public void onFailure(Call<KeranjangModel> call, Throwable t) {
                        Log.d("Fail", t.getLocalizedMessage());
                    }
                });
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(keranjangAdapter);
    }

    private void getDataMenuFromApi(String user_id, String token){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<KeranjangModel> callDalamProses = apiInterface.keranjangResponse(user_id,"Bearer "+token);
        callDalamProses.enqueue(new Callback<KeranjangModel>() {
            @Override
            public void onResponse(Call<KeranjangModel> call, Response<KeranjangModel> response) {
                if (response.isSuccessful()) {
                    List<KeranjangModel.KeranjangModelData> data = response.body().getData();
                    keranjangAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<KeranjangModel> call, Throwable t) {
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
