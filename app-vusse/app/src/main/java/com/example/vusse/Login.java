package com.example.vusse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.vusse.api.ApiClient;
import com.example.vusse.api.ApiInterface;
import com.example.vusse.model.login.LoginData;
import com.example.vusse.model.login.LoginModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    String sEmail, sPassword;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        AppCompatButton login = findViewById(R.id.login);
        TextView signup = findViewById(R.id.signup);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        login.setOnClickListener(v -> {
            sEmail = etEmail.getText().toString();
            sPassword = etPassword.getText().toString();

            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginModel> loginCall = apiInterface.loginResponse(sEmail, sPassword);
            loginCall.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.body() != null && response.isSuccessful()){
                        LoginData loginData = response.body().getUser();
                        sessionManager = new SessionManager(Login.this);
                        sessionManager.createLoginSession(loginData);

                        Toast.makeText(Login.this, sessionManager.getUserDetail().get("username"), Toast.LENGTH_SHORT).show();
                        Log.d("LoginTest", ""+loginData.getRoles().equals("admin"));
                        if (loginData.getRoles().equals("admin")) {
                            moveToPage(Login.this, BerandaAdmin.class);
                        }else if (loginData.getRoles().equals("user")){
                            moveToPage(Login.this, MainActivity.class);
                        }

                    }else {
                        Toast.makeText(Login.this, "Email dan Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        signup.setOnClickListener(v -> {
            moveToPage(Login.this, Signup.class);
        });

        sessionManager = new SessionManager(Login.this);
        if (sessionManager.isLoggedIn()) {
            moveToMain();
        }

    }

    private void moveToMain() {
        moveToPage(Login.this, MainActivity.class);
    }

    private void moveToPage(Login from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
}

