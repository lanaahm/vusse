package com.example.vusse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.login.LoginData;
import com.example.vusse.model.login.LoginModel;
import com.example.vusse.model.menu.MenuAdapter;
import com.example.vusse.model.menu.MenuModel;
import com.example.vusse.model.pesan.PesanModel;
import com.example.vusse.model.register.RegisterApiModelError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity {
    SessionManager sessionManager;
    ImageButton backMainAct;
    TextView profile;
    String restoranId, restoranName, ImageRestoLink, restoran_latitude, restoran_longitude;
    ImageView ImageResto;
    ImageButton market, maps;

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MenuAdapter menuAdapter;
    private List<MenuModel.MenuData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(Menu.this);

        restoranId = getIntent().getStringExtra("restoran_id");
        restoranName = getIntent().getStringExtra("restoran_name");
        ImageRestoLink = getIntent().getStringExtra("restoran_foto");
        restoran_latitude = getIntent().getStringExtra("restoran_latitude");
        restoran_longitude = getIntent().getStringExtra("restoran_longitude");
        
        Log.d("Text", restoran_latitude+", "+restoran_longitude);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_resto);

        profile = findViewById(R.id.profile);
        profile.setText(restoranName);
        ImageResto = findViewById(R.id.foto_pkl);

        Picasso.get().load(ImageRestoLink)
                .fit().into(ImageResto);

        maps = findViewById(R.id.maps);
        maps.setOnClickListener(v->{
            Intent intent = new Intent(Menu.this, Maps.class);
            intent.putExtra("restoran_latitude", restoran_latitude);
            intent.putExtra("restoran_longitude", restoran_longitude);
            startActivity(intent);
        });

        backMainAct = findViewById(R.id.cart);
        backMainAct.setOnClickListener(v -> {
            moveToPage(Menu.this, MainActivity.class);
        });

        market = findViewById(R.id.market);
        market.setOnClickListener(v->{
            moveToPage(Menu.this, MainActivity.class);
        });

        setupRecycle();
        setupRecycleResto(sessionManager.getUserDetail().get("user_id"), sessionManager.getUserDetail().get("token"));
        getDataMenuFromApi(sessionManager.getUserDetail().get("token"), restoranId);
    }

    private void setupRecycle() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void getDataMenuFromApi(String token, String restoranId) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MenuModel> callMenu = apiInterface.menusResponse(Integer.valueOf(restoranId),"Bearer "+token);
        callMenu.enqueue(new Callback<MenuModel>() {
            @Override
            public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                Log.d("DataMenu", response.toString());
                if (response.isSuccessful()) {
                    List<MenuModel.MenuData> data = response.body().getData();
                    menuAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<MenuModel> call, Throwable t) {
                Log.d("Fail", t.getLocalizedMessage());
            }
        });

    }

    private void setupRecycleResto(String user_id, String token) {
        menuAdapter = new MenuAdapter(data, new MenuAdapter.OnAdapterListener() {
            @Override
            public void onClick(MenuModel.MenuData result) {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<PesanModel> callMenu = apiInterface.pesanResponse("Bearer "+token, user_id, restoranId, String.valueOf(result.getId()));

                callMenu.enqueue(new Callback<PesanModel>() {
                    @Override
                    public void onResponse(Call<PesanModel> call, Response<PesanModel> response) {
                        if (response.body() != null && response.isSuccessful()){
                            String message = response.body().getMessage();
                            Toast.makeText(Menu.this, message, Toast.LENGTH_SHORT).show();
                        }else {
                            PesanModel message = new Gson().fromJson(response.errorBody().charStream(), PesanModel.class);
                            Toast.makeText(Menu.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PesanModel> call, Throwable t) {
                        Toast.makeText(Menu.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Menu.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);
    }

    private void moveToPage(Menu from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }

}
