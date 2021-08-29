package com.kagwisoftwares.inventory.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.adapters.DashAdapter;
import com.kagwisoftwares.inventory.adapters.ProductsAdapter;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.utils.GridSpacingItemDecoration;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.models.ItemModel;
import com.kagwisoftwares.inventory.db.MyViewModel;

import java.util.ArrayList;
import java.util.List;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsRecycler, productsRecycler;
    private MyViewModel myViewModel;
    private ProductsAdapter productsAdapter;
    private ArrayList<ItemModel> items;

    private FloatingActionsMenu addFab;
    private FloatingActionButton addPhone, addCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        productsRecycler = (RecyclerView) findViewById(R.id.productlistRecycler);
        itemsRecycler = (RecyclerView) findViewById(R.id.itemsRecycler);

        addFab = (FloatingActionsMenu) findViewById(R.id.addFab);
        addPhone = (FloatingActionButton) findViewById(R.id.addPhone);
        addCategory = (FloatingActionButton) findViewById(R.id.addCategory);

        LottieAnimationView emptyList = (LottieAnimationView) findViewById(R.id.emptyProductAnim);
        emptyList.setVisibility(View.GONE);

        addFab.removeButton(addPhone);
        addFab.removeButton(addCategory);

        addFab.addButton(addPhone);
        addFab.addButton(addCategory);

        addPhone.setTitle("New Phone");
        addCategory.setTitle("New Category");

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductItemActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        myViewModel.getAllProducts().observe(this, new Observer<List<ProductItem>>() {
            @Override
            public void onChanged(List<ProductItem> productItems) {
                setDashItems();
            }
        });

        myViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                int noOfProducts = categories.size();
                productsAdapter = new ProductsAdapter(categories, MainActivity.this);
                productsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                productsRecycler.setAdapter(productsAdapter);

                setDashItems();

                if (noOfProducts == 0) {
                    emptyList.setVisibility(View.VISIBLE);
                } else {
                    emptyList.setVisibility(View.GONE);
                }
                Log.d("CATEGORIES SIZE: ", String.valueOf(noOfProducts));
            }
        });
    }

    void setDashItems() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int allCategories = Inventorydb.getDatabase(getApplicationContext()).dao().getLastCategoryId();
                int allStockNo = Inventorydb.getDatabase(getApplicationContext()).dao().getTotalStockForShop();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        items = new ArrayList<>();
                        itemsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                        itemsRecycler.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
                        items.add(new ItemModel("Total Products", allCategories));
                        items.add(new ItemModel("Stock In Hand", allStockNo));
                        itemsRecycler.setAdapter(new DashAdapter(items));
                    }
                });
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_menu, menu);
        return true;
    }
}