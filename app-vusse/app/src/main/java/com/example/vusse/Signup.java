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
import com.example.vusse.model.register.RegisterApiModelError;
import com.example.vusse.model.register.RegisterModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    ApiInterface apiInterface;
    EditText etFullname, etEmail, etPhone, etPassword, etConfirm_password;
    String sFullname, sEmail, sPhone, sPassword, sConfirm_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        AppCompatButton signup = findViewById(R.id.signup);
        TextView login = findViewById(R.id.login);

        signup.setOnClickListener(v -> {
            sFullname = etFullname.getText().toString();
            sEmail = etEmail.getText().toString();
            sPhone = etPhone.getText().toString();
            sPassword = etPassword.getText().toString();
            sConfirm_password = etConfirm_password.getText().toString();

            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<RegisterModel> registerCall = apiInterface.registerResponse(sFullname, sEmail, sPassword, sConfirm_password);
            registerCall.enqueue(new Callback<RegisterModel>() {
                @Override
                public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                    Log.d("myTag", "msg scc: "+response.toString());
                    if (response.body() != null && response.isSuccessful()){
                        Toast.makeText(Signup.this, "Register Berhasil.", Toast.LENGTH_SHORT).show();
                        moveToPage(Signup.this, Login.class);
                    }else {
                        RegisterApiModelError message = new Gson().fromJson(response.errorBody().charStream(), RegisterApiModelError.class);
                        List<String> nama = message.getData().getName();
                        List<String> email = message.getData().getEmail();
                        List<String> password = message.getData().getPassword();
                        List<String> comfrim_password = message.getData().getConfirmPassword();
                        if (nama != null && !nama.isEmpty()) {
                            Toast.makeText(Signup.this, nama.get(0), Toast.LENGTH_SHORT).show();
                        }else if (email != null && !email.isEmpty()) {
                            Toast.makeText(Signup.this, email.get(0), Toast.LENGTH_SHORT).show();
                        }else if (password != null && !password.isEmpty()) {
                            Toast.makeText(Signup.this, password.get(0), Toast.LENGTH_SHORT).show();
                        }else if (sConfirm_password.isEmpty()) {
                            Toast.makeText(Signup.this, comfrim_password.get(0), Toast.LENGTH_SHORT).show();
                        }else if (!sConfirm_password.equals(sPassword)) {
                            Toast.makeText(Signup.this, "The confirm password and password must match.", Toast.LENGTH_SHORT).show();
                        }else if (sPhone == "") {
                            Toast.makeText(Signup.this, "Phone Tidak Boleh kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RegisterModel> call, Throwable t) {
                    Toast.makeText(Signup.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        login.setOnClickListener(v -> {
            moveToPage(Signup.this, Login.class);
        });
    }

    private void moveToPage(Signup from, Class to) {
        Intent intent = new Intent(from, to);
        startActivity(intent);
    }
}