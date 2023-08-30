package com.example.vusse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.detailpesananadmin.DetailPesananAdminAdapter;
import com.example.vusse.model.detailpesananadmin.DetailPesananAdminModel;
import com.example.vusse.model.login.LoginData;
import com.example.vusse.model.updatestatus.UpdateStatusData;
import com.example.vusse.model.updatestatus.UpdateStatusModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPemesananUser extends AppCompatActivity {
    TextView admin_name, nama_resto;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    ImageButton cart;
    AppCompatButton ordercomplate;
    String transactions_id, restoranId, restoranName, userId, userName;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DetailPesananAdminAdapter detailPesananAdminAdapter;
    private List<DetailPesananAdminModel.DetailPesananAdminModelData> data = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pemesanan_user);
        sessionManager = new SessionManager(this);

        transactions_id = getIntent().getStringExtra("transactions_id");
        restoranId = getIntent().getStringExtra("restaurant_id");
        restoranName = getIntent().getStringExtra("restaurant_name");
        userId = getIntent().getStringExtra("user_id");
        userName = getIntent().getStringExtra("user_name");

        admin_name = findViewById(R.id.admin_name);
        admin_name.setText(userName);
        nama_resto = findViewById(R.id.nama_resto);
        nama_resto.setText(restoranName);

        cart = findViewById(R.id.cart);
        cart.setOnClickListener(v->{
            moveToPage(DetailPemesananUser.this, BerandaAdmin.class);
        });

        ordercomplate = findViewById(R.id.ordercomplate);
        ordercomplate.setOnClickListener(v->{
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateStatusModel> callUpdateStatus = apiInterface.updateStatusResponse(userId, transactions_id, "Bearer "+sessionManager.getUserDetail().get("token"));
            Log.d("Respon update", callUpdateStatus.toString());
            callUpdateStatus.enqueue(new Callback<UpdateStatusModel>() {
                @Override
                public void onResponse(Call<UpdateStatusModel> call, Response<UpdateStatusModel> response) {
                    Log.d("Respon update", response.toString());
                    if (response.isSuccessful()) {
                        UpdateStatusData updateStatus = response.body().getData();
                        Toast.makeText(DetailPemesananUser.this, "Transaksi Berhasil Diproses", Toast.LENGTH_SHORT).show();
                        moveToPage(DetailPemesananUser.this, BerandaAdmin.class);
                    }else{
                        Toast.makeText(DetailPemesananUser.this, "Transaksi Gagal Diproses", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<UpdateStatusModel> call, Throwable t) {
                    Log.d("Fail", t.getLocalizedMessage());
                }
            });
        });

        setupRecycle();
        setupRecycleResto();
        getDataMenuFromApi(userId, restoranId, sessionManager.getUserDetail().get("token"));
    }


    private void setupRecycle() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void getDataMenuFromApi(String userId, String restoranId, String token) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DetailPesananAdminModel> callMenu = apiInterface.detailPesananAdminResponse(userId, restoranId, "Bearer "+token);
        callMenu.enqueue(new Callback<DetailPesananAdminModel>() {
            @Override
            public void onResponse(Call<DetailPesananAdminModel> call, Response<DetailPesananAdminModel> response) {
                Log.d("DataMenu", response.toString());
                if (response.isSuccessful()) {
                    List<DetailPesananAdminModel.DetailPesananAdminModelData> data = response.body().getData();
                    detailPesananAdminAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<DetailPesananAdminModel> call, Throwable t) {
                Log.d("Fail", t.getLocalizedMessage());
            }
        });

    }

    private void setupRecycleResto() {
        detailPesananAdminAdapter = new DetailPesananAdminAdapter(data, new DetailPesananAdminAdapter.OnAdapterListener(){
            @Override
            public void onClick(DetailPesananAdminModel.DetailPesananAdminModelData result) {}
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailPesananAdminAdapter);
    }

    private void moveToPage(FragmentActivity from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
}
