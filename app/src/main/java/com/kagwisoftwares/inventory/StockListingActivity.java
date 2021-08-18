package com.kagwisoftwares.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kagwisoftwares.inventory.adapters.StockListingAdapter;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.ProductItem;
import com.kagwisoftwares.inventory.utils.MyTransitions;

import java.util.List;

public class StockListingActivity extends AppCompatActivity {

    private StockListingAdapter stocksAdapter;
    private RecyclerView stockRecycler;

    private List<ProductItem> productItems;

    private LottieAnimationView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyTransitions().animateFade(this);
        setContentView(R.layout.activity_stock_listing);
        getSupportActionBar().setTitle("All Stock");

        stockRecycler = (RecyclerView) findViewById(R.id.stockRecycler);
        emptyList = (LottieAnimationView) findViewById(R.id.emptyProductAnim);
        emptyList.setVisibility(View.VISIBLE);

        getStock(getIntent().getIntExtra("CATEGORY_ID", 0));

    }
    
    void getStock(int itemId) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                productItems = Inventorydb.getDatabase(getApplicationContext()).dao().getProductItemsById(itemId);
                Log.d("PHONES BY ID ", productItems.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (productItems.size() == 0)
                            emptyList.setVisibility(View.VISIBLE);
                        else
                            emptyList.setVisibility(View.GONE);

                        stocksAdapter = new StockListingAdapter(productItems);
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
