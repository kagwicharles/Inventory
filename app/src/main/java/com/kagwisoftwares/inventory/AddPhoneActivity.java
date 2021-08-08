package com.kagwisoftwares.inventory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kagwisoftwares.inventory.utils.MyTransitions;

public class AddPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyTransitions().animateFade(this);
        setContentView(R.layout.activity_add_phone);
        getSupportActionBar().setTitle("New Shipment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
