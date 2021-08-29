package com.kagwisoftwares.inventory.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthenticateApp {

    private final Context context;
    private SharedPreferences sharedPreferences;

    public AuthenticateApp(Context context) {
        this.context = context;
    }

    public void storeCredentials(String e_mail, String pass) {
        sharedPreferences = context.
                getSharedPreferences("AUTHENTICATION_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("E-MAIL", e_mail);
        editor.putString("PASS", pass);
        editor.apply();
    }

    public boolean readCredentials() {
        boolean loginExists = true;
        sharedPreferences = context.getSharedPreferences("AUTHENTICATION_PREFS", MODE_PRIVATE);
        String username = sharedPreferences.getString("E-MAIL",null);
        String password = sharedPreferences.getString("PASS",null);
        if (username == null || password == null)
            loginExists = false;
        return loginExists;
    }

    public void clearCredentials() {
        sharedPreferences = context.getSharedPreferences("AUTHENTICATION_PREFS", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
