package com.kagwisoftwares.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kagwisoftwares.inventory.adapters.StockListingAdapter;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Phone;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;
import com.kagwisoftwares.inventory.viewmodels.MyViewModel;

import java.util.List;

public class StockListingActivity extends AppCompatActivity {

    private StockListingAdapter stocksAdapter;
    private RecyclerView stockRecycler;

    private List<Phone> phones;

    private LottieAnimationView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_listing);
        getSupportActionBar().setTitle("Available Stock");

        stockRecycler = (RecyclerView) findViewById(R.id.stockRecycler);
        emptyList = (LottieAnimationView) findViewById(R.id.emptyProductAnim);
        emptyList.setVisibility(View.VISIBLE);

        getStock(getIntent().getIntExtra("CATEGORY_ID", 0));

    }
    
    void getStock(int itemId) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                phones = Inventorydb.getDatabase(getApplicationContext()).dao().getPhonesById(itemId);
                Log.d("PHONES BY ID ", phones.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (phones.size() == 0)
                            emptyList.setVisibility(View.VISIBLE);
                        else
                            emptyList.setVisibility(View.GONE);

                        stocksAdapter = new StockListingAdapter(phones);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(StockListingActivity.this, LinearLayoutManager.VERTICAL, false);
                        stockRecycler.setLayoutManager(layoutManager);
                        stockRecycler.addItemDecoration(new DividerItemDecoration(stockRecycler.getContext(), layoutManager.getOrientation()));
                        stockRecycler.setAdapter(stocksAdapter);
                    }
                });
            }
        };
        thread.start();
    }
}
