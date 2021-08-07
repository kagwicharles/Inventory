package com.kagwisoftwares.inventory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone);
        getSupportActionBar().setTitle("New Shipment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
