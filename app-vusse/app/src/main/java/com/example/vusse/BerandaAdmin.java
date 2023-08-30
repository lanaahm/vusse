package com.example.vusse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.listorderadmin.ListOrderAdminAdapter;
import com.example.vusse.model.listorderadmin.ListOrderAdminModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaAdmin extends AppCompatActivity {
    ImageButton person;
    TextView admin_name;
    String username;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ListOrderAdminAdapter listOrderAdminAdapter;
    private List<ListOrderAdminModel.ListOrderAdminData> data = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beranda_admin);
        sessionManager = new SessionManager(this);
        username = sessionManager.getUserDetail().get("username");
        admin_name = findViewById(R.id.admin_name);
        admin_name.setText("Hallo, "+username);

        person = findViewById(R.id.person);
        person.setOnClickListener(v->{
            moveToPage(BerandaAdmin.this, ProfileAdmin.class);
        });

        setupRecycle();
        setupRecycleResto();
        getDataMenuFromApi(sessionManager.getUserDetail().get("token"));
    }


    private void setupRecycle() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void getDataMenuFromApi(String token) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ListOrderAdminModel> callMenu = apiInterface.listOrderAdminResponse("Bearer "+token);
        callMenu.enqueue(new Callback<ListOrderAdminModel>() {
            @Override
            public void onResponse(Call<ListOrderAdminModel> call, Response<ListOrderAdminModel> response) {
                Log.d("DataMenu", response.toString());
                if (response.isSuccessful()) {
                    List<ListOrderAdminModel.ListOrderAdminData> data = response.body().getData();
                    listOrderAdminAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<ListOrderAdminModel> call, Throwable t) {
                Log.d("Fail", t.getLocalizedMessage());
            }
        });

    }

    private void setupRecycleResto() {
        listOrderAdminAdapter = new ListOrderAdminAdapter(data, new ListOrderAdminAdapter.OnAdapterListener(){
            @Override
            public void onClick(ListOrderAdminModel.ListOrderAdminData result) {
                Intent intent = new Intent(BerandaAdmin.this, DetailPemesananUser.class);
                intent.putExtra("transactions_id", String.valueOf(result.getId()));
                intent.putExtra("restaurant_id", String.valueOf(result.getRestauranId()));
                intent.putExtra("restaurant_name", String.valueOf(result.getRestaurantsName()));
                intent.putExtra("user_id", String.valueOf(result.getUserId()));
                intent.putExtra("user_name", String.valueOf(result.getUserName()));
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listOrderAdminAdapter);
    }

    private void moveToPage(FragmentActivity from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
}
