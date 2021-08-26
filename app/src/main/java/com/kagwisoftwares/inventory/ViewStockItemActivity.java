package com.kagwisoftwares.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kagwisoftwares.inventory.adapters.AttributesAdapter;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.ProductAttribute;

import java.util.ArrayList;
import java.util.List;

public class ViewStockItemActivity extends AppCompatActivity {

    private AttributesAdapter attributesAdapter;
    private RecyclerView attributesRecycler;
    private TextView stockName, stockUpdate, stockSize;
    private String itemName, itemLastUpdated, itemStockSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stock_item);
        getSupportActionBar().setTitle("Stock Details");

        attributesRecycler = (RecyclerView) findViewById(R.id.attributesRecycler);
        stockName = findViewById(R.id.stockItem);
        stockUpdate = findViewById(R.id.item_update_date);
        stockSize = findViewById(R.id.item_stock_size);

        itemName = getIntent().getStringExtra("ITEM_NAME");
        itemLastUpdated = getIntent().getStringExtra("ITEM_UPDATE");
        itemStockSize = getIntent().getStringExtra("ITEM_STOCK");

        Button editSale = findViewById(R.id.editSaleItem);
        Button editStock = findViewById(R.id.editStockItem);

        editSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaleStockDialog saleStockDialog = new SaleStockDialog();
                saleStockDialog.show(getSupportFragmentManager(), "Sale Stock Dialog");
            }
        });

        editStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        stockName.setText(itemName);
        stockUpdate.setText(itemLastUpdated);
        stockSize.setText(itemStockSize);

        Thread thread = new Thread() {
            List<ProductAttribute> productAttributes = new ArrayList<>();
            @Override
            public void run() {
                int productId = Inventorydb.
                        getDatabase(getApplicationContext()).
                        dao().getProductIdByName(stockName.getText().toString());
                productAttributes = Inventorydb.
                        getDatabase(getApplicationContext()).
                        dao().getAllAttributesById(productId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        attributesAdapter = new AttributesAdapter(productAttributes);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        attributesRecycler.setLayoutManager(linearLayoutManager);
                        attributesRecycler.addItemDecoration(new DividerItemDecoration(attributesRecycler.getContext(), linearLayoutManager.getOrientation()));
                        attributesRecycler.setAdapter(attributesAdapter);
                    }
                });
            }
        };
        thread.start();
    }
}
