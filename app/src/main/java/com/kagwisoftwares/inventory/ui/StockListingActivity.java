package com.kagwisoftwares.inventory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.adapters.StockListingAdapter;
import com.kagwisoftwares.inventory.db.MyViewModel;
import com.kagwisoftwares.inventory.db.entities.ProductItem;

import java.util.List;
import java.util.concurrent.ExecutorService;

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
        setSupportActionBar(toolbar);
        toolbar.setTitle("All Stock");

        stockRecycler = (RecyclerView) findViewById(R.id.stockRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(StockListingActivity.this, LinearLayoutManager.VERTICAL, false);
        stockRecycler.setLayoutManager(layoutManager);
        stockRecycler.addItemDecoration(new DividerItemDecoration(stockRecycler.getContext(), layoutManager.getOrientation()));

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
                stockRecycler.setAdapter(stocksAdapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stock_view_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                stocksAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
