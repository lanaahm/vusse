package com.example.vusse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.login.LoginData;
import com.example.vusse.model.login.LoginModel;
import com.example.vusse.model.login.Logout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    ImageButton backMainAct;
    SessionManager sessionManager;
    TextView tvUsername, tvEmail, tvNotelp;
    AppCompatButton vLogin;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        sessionManager = new SessionManager(Profile.this);

        tvUsername = findViewById(R.id.username);
        tvEmail = findViewById(R.id.email_profile);
        tvNotelp = findViewById(R.id.notelp_profile);
        vLogin = findViewById(R.id.login);

        tvUsername.setText(sessionManager.getUserDetail().get("username"));
        tvEmail.setText(sessionManager.getUserDetail().get("email"));
        tvNotelp.setText(sessionManager.getUserDetail().get("phone"));

        vLogin.setOnClickListener(v -> {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Logout> loginCall = apiInterface.logoutResponse("Bearer "+sessionManager.getUserDetail().get("token"));
            loginCall.enqueue(new Callback<Logout>() {
                @Override
                public void onResponse(Call<Logout> call, Response<Logout> response) {
                    if (response.body() != null && response.isSuccessful()){
                        String messeage = response.body().getMessage();
                        sessionManager.LogoutSession();
                        Toast.makeText(Profile.this, messeage, Toast.LENGTH_SHORT).show();
                        moveToPage(Profile.this, Login.class);
                    }
                }

                @Override
                public void onFailure(Call<Logout> call, Throwable t) {
                    Toast.makeText(Profile.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        backMainAct = findViewById(R.id.cart);
        backMainAct.setOnClickListener(v -> {
            moveToPage(Profile.this, MainActivity.class);
        });

    }

    private void moveToPage(Profile from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
}
