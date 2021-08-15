package com.kagwisoftwares.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;
import com.kagwisoftwares.inventory.utils.MyTransitions;
import com.kagwisoftwares.inventory.viewmodels.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddPhoneActivity extends AppCompatActivity {

    private AutoCompleteTextView categories, ram, storage, primaryCamera;
    private EditText subCategory, OS, noOfItems;

    private MyViewModel myViewModel;

    private String category, sub_category, ramSize, storageSize, camPixels, osVersion;
    private int units;
    private static int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MyTransitions().animateFade(this);
        setContentView(R.layout.activity_add_phone);
        getSupportActionBar().setTitle("New Shipment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        Button saveItem = (Button) findViewById(R.id.saveItem);

        categories = (AutoCompleteTextView) findViewById(R.id.categoryAutoComplete);
        ram = (AutoCompleteTextView) findViewById(R.id.ramAutoComplete);
        storage = (AutoCompleteTextView) findViewById(R.id.memAutoComplete);
        primaryCamera = (AutoCompleteTextView) findViewById(R.id.cameraAutoComplete);

        subCategory = (EditText) findViewById(R.id.etsubCategory);
        OS = (EditText) findViewById(R.id.osVersion);
        noOfItems = (EditText) findViewById(R.id.noOfItems);

        addDropDownItems();

        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

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

    void saveItem() {
        Thread thread = new Thread() {
            int itemId;

            @Override
            public void run() {
                boolean allValuesCorrect = getFormValues();
                Log.d("PRODUCT CATEGORY", category);
                itemId = Inventorydb.getDatabase(getApplicationContext()).dao().getCategory(category);
                Log.d("PHONE CATEGORY ID ", String.valueOf(itemId));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (allValuesCorrect) {
                            Phone phone = new Phone();
                            phone.setCategoryId(itemId);
                            phone.setPhoneCategory(sub_category);
                            phone.setPhoneUnits(units);

                            if (!ramSize.equals(""))
                                phone.setPhoneRam(ramSize);
                            if (!storageSize.equals(""))
                                phone.setPhoneStorage(storageSize);
                            if (!camPixels.equals(""))
                                phone.setPrimaryCameraPixels(camPixels);
                            if (!osVersion.equals(""))
                                phone.setOsVersion(osVersion);
                            try {
                                myViewModel.insertPhone(phone);
                                Toast.makeText(getApplicationContext(), "Item saved", Toast.LENGTH_SHORT).show();
                            } catch (Error e) {
                                Toast.makeText(getApplicationContext(), "Item not saved", Toast.LENGTH_SHORT).show();
                                Log.d("ERROR ", e.toString());
                            }
                        }
                    }
                });
            }
        };
        thread.start();
    }

    boolean getFormValues() {
        boolean valuesCorrect = false;

        category = categories.getEditableText().toString();
        sub_category = subCategory.getText().toString();
        ramSize = ram.getEditableText().toString();
        storageSize = storage.getEditableText().toString();
        camPixels = primaryCamera.getEditableText().toString();
        osVersion = OS.getText().toString();

        try {
            units = Integer.parseInt(noOfItems.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (!missingFields())
            valuesCorrect = true;

        return valuesCorrect;
    }

    boolean missingFields() {
        boolean missingField = false;
        if (category.equals("")) {
            categories.setError("Select category");
            missingField = true;
        }

        if (sub_category.equals("")) {
            subCategory.setError("Product category");
            missingField = true;
        }

        if (units < 1) {
            noOfItems.setError("Units to add");
            missingField = true;
        }
        return missingField;
    }
}
