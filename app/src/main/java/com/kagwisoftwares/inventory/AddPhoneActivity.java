package com.kagwisoftwares.inventory;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.utils.MyTransitions;
import com.kagwisoftwares.inventory.view_model.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddPhoneActivity extends AppCompatActivity {

    private AutoCompleteTextView categories, ram, storage, primaryCamera;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyTransitions().animateFade(this);
        setContentView(R.layout.activity_add_phone);
        getSupportActionBar().setTitle("New Shipment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        categories = (AutoCompleteTextView) findViewById(R.id.categoryAutoComplete);
        ram = (AutoCompleteTextView) findViewById(R.id.ramAutoComplete);
        storage = (AutoCompleteTextView) findViewById(R.id.memAutoComplete);
        primaryCamera = (AutoCompleteTextView) findViewById(R.id.cameraAutoComplete);

        addDropDownItems();

    }

    void addDropDownItems() {
        getCategoryItems();

        String[] ramItems = {"1 GB", "2 GB", "4 GB", "8 GB", "16 GB", "32 GB"};
        ram.setAdapter(new ArrayAdapter(this, R.layout.list_item, ramItems));
        ram.setText(ram.getAdapter().getItem(0).toString(), false);

        String[] storageItems = {"4 GB", "8 GB", "16 GB", "32 GB", "64 GB"};
        storage.setAdapter(new ArrayAdapter(this, R.layout.list_item, storageItems));
        storage.setText(storage.getAdapter().getItem(0).toString(), false);

        String[] frontCamItems = {"2MP", "5MP", "8MP", "16MP", "32MP"};
        primaryCamera.setAdapter(new ArrayAdapter(this, R.layout.list_item, frontCamItems));
        primaryCamera.setText(primaryCamera.getAdapter().getItem(0).toString(), false);
    }

    void getCategoryItems() {
        List<String> categoryArray = new ArrayList<>();

        myViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryItems) {
                for (int i = 0; i < categoryItems.size(); i++) {
                    categoryArray.add(categoryItems.get(i).getCategory_name());
                }
            }
        });
        categories.setAdapter(new ArrayAdapter(this, R.layout.list_item, categoryArray));
        categories.setSelection(0);
    }
}
