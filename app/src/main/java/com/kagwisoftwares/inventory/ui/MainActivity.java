package com.kagwisoftwares.inventory.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.adapters.DashAdapter;
import com.kagwisoftwares.inventory.adapters.ProductsAdapter;
import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.utils.AuthenticateApp;
import com.kagwisoftwares.inventory.utils.GridSpacingItemDecoration;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.models.ItemModel;
import com.kagwisoftwares.inventory.db.MyViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.kagwisoftwares.inventory.utils.ThreadAllCategories;
import com.kagwisoftwares.inventory.utils.ThreadAllStock;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsRecycler, productsRecycler;
    private MyViewModel myViewModel;
    private ProductsAdapter productsAdapter;
    private ArrayList<ItemModel> items;
    private List<Category> categoriesList;

    private FloatingActionsMenu addFab;
    private FloatingActionButton addPhone, addCategory;

    private ExecutorService service;

    private final String[] options = new String[]{"Alphabetical - Ascending", "Alphabetical - Descending", "Oldest first", "Newest first"};
    private static final int ASCENDING = 0;
    private static final int DESCENDING = 1;
    private static final int OLDEST_FIRST = 2;
    private static final int NEWEST_FIRST = 3;
    private static int sort_choice = 2;

    private AuthenticateApp authenticateApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        authenticateApp = new AuthenticateApp(this);
        checkAuthentication();

        categoriesList = new ArrayList<>();

        productsRecycler = (RecyclerView) findViewById(R.id.productlistRecycler);
        productsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        itemsRecycler = (RecyclerView) findViewById(R.id.itemsRecycler);
        itemsRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        itemsRecycler.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));

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

        service = Executors.newFixedThreadPool(3);

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
                categoriesList = categories;
                productsAdapter = new ProductsAdapter(categories, MainActivity.this);
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

        ImageView sort = findViewById(R.id.sortItems);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortConfirmationDialog();
            }
        });
    }

    void setDashItems() {
        Future<String> categoriesTotal = service.submit(new ThreadAllCategories(getApplicationContext()));
        Future<String> totalStock = service.submit(new ThreadAllStock(getApplicationContext()));
        items = new ArrayList<>();
        try {
            items.add(new ItemModel("Total Products", Integer.parseInt(categoriesTotal.get())));
            items.add(new ItemModel("Stock In Hand", Integer.parseInt(totalStock.get())));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        itemsRecycler.setAdapter(new DashAdapter(items));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            authenticateApp.clearCredentials();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }


    private void showSortConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.RadioDialogTheme);
        builder.setTitle("Sort Items");
        builder.setSingleChoiceItems(options, sort_choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortAllItems(which);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void sortAllItems(int choice) {
        switch (choice) {
            case ASCENDING:
                Collections.sort(categoriesList, Category.CategoryNameAZComparator);
                productsAdapter.notifyDataSetChanged();
                break;
            case DESCENDING:
                Collections.sort(categoriesList, Category.CategoryNameZAComparator);
                productsAdapter.notifyDataSetChanged();
                break;
            case OLDEST_FIRST:
                Collections.sort(categoriesList, Category.CategoryDateAscendingComparator);
                productsAdapter.notifyDataSetChanged();
                break;
            case NEWEST_FIRST:
                Collections.sort(categoriesList, Category.CategoryDateDescendingComparator);
                productsAdapter.notifyDataSetChanged();
                break;

        }
    }

    void checkAuthentication() {
        if (!authenticateApp.readCredentials()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}