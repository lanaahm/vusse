package com.example.vusse;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.vusse.model.login.LoginData;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(LoginData user){
        editor.putBoolean("isLoggedIn", true);
        editor.putString("user_id", String.valueOf(user.getId()));
        editor.putString("username", user.getName());
        editor.putString("phone", user.getPhone());
        editor.putString("email", user.getEmail());
        editor.putString("token", user.getToken());
        editor.apply();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put("user_id", sharedPreferences.getString("user_id", ""));
        user.put("username", sharedPreferences.getString("username", ""));
        user.put("phone", sharedPreferences.getString("phone", ""));
        user.put("email", sharedPreferences.getString("email", ""));
        user.put("token", sharedPreferences.getString("token", ""));
        return user;
    }

    public void LogoutSession(){
        editor.clear().apply();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}
