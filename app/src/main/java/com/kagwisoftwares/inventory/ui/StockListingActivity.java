package com.kagwisoftwares.inventory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.adapters.StockListingAdapter;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.db.MyViewModel;
import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.utils.MyThread;
import com.kagwisoftwares.inventory.utils.ThreadCategoryId;
import com.kagwisoftwares.inventory.utils.ThreadProductTotalById;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StockListingActivity extends AppCompatActivity {

    private StockListingAdapter stocksAdapter;
    private RecyclerView stockRecycler;
    private MyViewModel myViewModel;
    private LottieAnimationView emptyList;
    private ExecutorService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_listing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Stock");

        stockRecycler = (RecyclerView) findViewById(R.id.stockRecycler);
        emptyList = (LottieAnimationView) findViewById(R.id.emptyProductAnim);
        emptyList.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        int categoryId = Integer.parseInt(intent.getStringExtra("CATEGORY_ID"));

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.getAllProductsById(getApplication(), categoryId).observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
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
}
