package com.kagwisoftwares.inventory.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.adapters.AttributesAdapter;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.db.entities.ProductAttribute;

import java.util.ArrayList;
import java.util.List;

public class ViewStockItemActivity extends AppCompatActivity {

    private AttributesAdapter attributesAdapter;
    private RecyclerView attributesRecycler;
    private TextView stock;
    private ImageView productImage;

    private String itemName, itemLastUpdated, itemStockSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock_item);

        Toolbar toolbar = findViewById(R.id.toolbar_product_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemName = getIntent().getStringExtra("ITEM_NAME");
        itemLastUpdated = getIntent().getStringExtra("ITEM_UPDATE");
        itemStockSize = getIntent().getStringExtra("ITEM_STOCK");

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsible_toolbar_product);
        collapsingToolbarLayout.setTitle(itemName);

        productImage = findViewById(R.id.image_product_config_item_photo);

        TextView updated = findViewById(R.id.tv_updated);
        updated.setText(itemLastUpdated);
        stock = findViewById(R.id.tv_stock);

        updateUI();

        attributesRecycler = findViewById(R.id.attributesRecycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stock_config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sellItem:
                SaleStockDialog saleStockDialog = new SaleStockDialog();
                saleStockDialog.show(getSupportFragmentManager(), "about");
                setBundle(saleStockDialog);
                return true;
            case R.id.receive_stock:
                ReceiveStockDialog receiveStockDialog = new ReceiveStockDialog();
                receiveStockDialog.show(getSupportFragmentManager(), "about");
                setBundle(receiveStockDialog);
                return true;
            case R.id.deleteItem:
                DeleteStockDialog deleteStockDialog = new DeleteStockDialog();
                deleteStockDialog.show(getSupportFragmentManager(), "about");
                setBundle(deleteStockDialog);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void setBundle(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("STOCK_SIZE", itemStockSize);
        bundle.putString("STOCK_NAME", itemName);
        fragment.setArguments(bundle);
    }

    void updateUI() {
        Thread thread = new Thread() {
            List<ProductAttribute> productAttributes = new ArrayList<>();

            @Override
            public void run() {
                int productId = Inventorydb.
                        getDatabase(getApplicationContext()).
                        dao().getProductIdByName(itemName);
                int stockSize = Inventorydb.
                        getDatabase(getApplicationContext()).
                        dao().getTotalUnitsById(productId);
                byte[] itemImage = Inventorydb.getDatabase(getApplicationContext()).
                        dao().getProductImageById(productId);
                productAttributes = Inventorydb.
                        getDatabase(getApplicationContext()).
                        dao().getAllAttributesById(productId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        attributesAdapter = new AttributesAdapter(productAttributes);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        attributesRecycler.setLayoutManager(linearLayoutManager);
                        attributesRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation()));
                        attributesRecycler.setAdapter(attributesAdapter);
                        stock.setText(String.valueOf(stockSize));
                        productImage.setImageBitmap(setImage(itemImage));
                    }
                });
            }
        };
        thread.start();
    }

    public Bitmap setImage(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
