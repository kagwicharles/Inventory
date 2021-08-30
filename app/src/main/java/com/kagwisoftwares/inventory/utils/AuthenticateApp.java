package com.kagwisoftwares.inventory.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AuthenticateApp {

    private final Context context;
    private SharedPreferences sharedPreferences;
    private LocalDate today;
    private long daysPassed;

    public AuthenticateApp(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }
    }

    public void storeCredentials(String username, String pass, LocalDate lastLogin) {
        sharedPreferences = context.
                getSharedPreferences("AUTHENTICATION_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", username);
        editor.putString("PASS", pass);
        editor.putString("LAST_LOGIN", lastLogin.toString());
        editor.apply();
    }

    public boolean readCredentials() {
        boolean loginExists = true;
        sharedPreferences = context.getSharedPreferences("AUTHENTICATION_PREFS", MODE_PRIVATE);
        String username = sharedPreferences.getString("USER",null);
        String password = sharedPreferences.getString("PASS",null);
        String lastLoginDate = sharedPreferences.getString("LAST_LOGIN", null);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O && lastLoginDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate localDate = LocalDate.parse(lastLoginDate, formatter);
            daysPassed = ChronoUnit.DAYS.between(localDate, today);
            Log.d("DAYS PASSED", String.valueOf(daysPassed));
        }
        if (username == null || password == null || daysPassed >= 5)
            loginExists = false;
        return loginExists;
    }

    public void clearCredentials() {
        sharedPreferences = context.getSharedPreferences("AUTHENTICATION_PREFS", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
