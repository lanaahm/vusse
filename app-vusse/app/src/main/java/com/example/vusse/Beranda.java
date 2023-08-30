package com.example.vusse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.restoran.RestoranAdapter;
import com.example.vusse.model.restoran.RestoranModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Beranda extends Fragment {
    SessionManager sessionManager;
    TextView halloUser;
    EditText search;
    ImageButton btnKeranjang, btnPerson;
    String username;

    ApiInterface apiInterface;
    RecyclerView recyclerView;
    RestoranAdapter restoranAdapter;
    private List<RestoranModel.RestoranData> data = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.beranda, container, false);
        sessionManager = new SessionManager(container.getContext());
        username = sessionManager.getUserDetail().get("username");
        halloUser = view.findViewById(R.id.name);
        halloUser.setText("Hallo, "+username);

        btnKeranjang = view.findViewById(R.id.cart);
        btnKeranjang.setOnClickListener(v -> {
            loadFragment(new DalamProses());
        });

        btnPerson = view.findViewById(R.id.person);
        btnPerson.setOnClickListener(v -> {
            moveToPage(getActivity(), Profile.class);
        });

        setupRecycle(view);
        setupRecycleResto();
        getDataRestoFromApi(sessionManager.getUserDetail().get("token"));
        return view;
    }

    private void setupRecycle(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setupRecycleResto() {
        restoranAdapter = new RestoranAdapter(data, new RestoranAdapter.OnAdapterListener() {
            @Override
            public void onClick(RestoranModel.RestoranData result) {
                Intent intent = new Intent(getActivity(), Menu.class);
                intent.putExtra("restoran_id", String.valueOf(result.getId()));
                intent.putExtra("restoran_name", result.getName());
                intent.putExtra("restoran_foto", result.getFoto());
                intent.putExtra("restoran_latitude", result.getLatitude());
                intent.putExtra("restoran_longitude", result.getLongitude());
                getActivity().startActivity(intent);

                Toast.makeText(getContext(), result.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(restoranAdapter);
    }

    private void getDataRestoFromApi(String token){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RestoranModel> callRestoran = apiInterface.restaurantsResponse("Bearer "+token);
        callRestoran.enqueue(new Callback<RestoranModel>() {
            @Override
            public void onResponse(Call<RestoranModel> call, Response<RestoranModel> response) {
                if (response.isSuccessful()) {
                    List<RestoranModel.RestoranData> data = response.body().getData();
                    restoranAdapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<RestoranModel> call, Throwable t) {
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

    private void moveToPage(FragmentActivity from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
}
