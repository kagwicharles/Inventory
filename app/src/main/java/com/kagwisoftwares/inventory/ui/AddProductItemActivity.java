package com.kagwisoftwares.inventory.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.kagwisoftwares.inventory.R;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductAttribute;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.db.MyRepository;
import com.kagwisoftwares.inventory.db.MyViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProductItemActivity extends AppCompatActivity {

    private AutoCompleteTextView categories;
    private Button saveItem, addAttribute;
    private LinearLayout linearLayout;
    private TextInputEditText et_productName;
    private ImageView addUnitValue, removeUnitValue;
    private TextView txt_totalUnits;

    private MyViewModel myViewModel;
    private String category, productName;
    private int productId, totalUnits;
    private ArrayList<ProductAttribute> productAttributes;
    private MyRepository myRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Item");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        saveItem = findViewById(R.id.saveItem);
        addAttribute = findViewById(R.id.add_attribute);
        categories = findViewById(R.id.categoryAutoComplete);
        linearLayout = findViewById(R.id.product_attributes);
        et_productName = findViewById(R.id.et_productName);
        addUnitValue = findViewById(R.id.addUnitValue);
        removeUnitValue = findViewById(R.id.removeUnitValue);
        txt_totalUnits = findViewById(R.id.totalUnits);

        productAttributes = new ArrayList<>();
        myRepository = new MyRepository(getApplication());

        addNewView();
        addDropDownItems();

        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });

        addAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewView();
            }
        });

        addUnitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementUnitValue();
            }
        });

        removeUnitValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementUnitValue();
            }
        });

    }

    void incrementUnitValue() {
        totalUnits = Integer.parseInt(txt_totalUnits.getText().toString());
        totalUnits++;
        txt_totalUnits.setText(String.valueOf(totalUnits));
    }

    void decrementUnitValue() {
        totalUnits = Integer.parseInt(txt_totalUnits.getText().toString());
        totalUnits--;
        txt_totalUnits.setText(String.valueOf(totalUnits));
    }

    void addDropDownItems() {
        getCategoryItems();
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

    void addNewView() {
        View attributeView = getLayoutInflater().inflate(R.layout.row_add_product_item, null, false);
        ImageView close = attributeView.findViewById(R.id.remove_attribute);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(attributeView);
            }
        });
        linearLayout.addView(attributeView);
    }

    void removeView(View itemView) {
        linearLayout.removeView(itemView);
    }

    void saveItem() {
        Thread thread = new Thread() {
            int itemId;

            @Override
            public void run() {
                boolean allValuesCorrect = getFormValues();
                itemId = Inventorydb.getDatabase(getApplicationContext()).dao().getCategory(category);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("PRODUCT CATEGORY", category);
                        Log.d("PHONE CATEGORY ID ", String.valueOf(itemId));
                        if (allValuesCorrect) {
                            ProductItem productItem = new ProductItem();
                            productItem.setItem_name(productName);
                            productItem.setCategoryId(itemId);
                            productItem.setDate(Calendar.getInstance().getTime());
                            productItem.setItem_units(totalUnits);
                            myViewModel.insertProductItem(productItem);
                            insertProductAttributes(productName, productAttributes);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to save", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        thread.start();
    }

    boolean getFormValues() {
        productAttributes.clear();
        boolean valuesCorrect = true;
        category = categories.getEditableText().toString();
        productName = et_productName.getText().toString();
        if (category.equals("") || productName.equals("")) {
            return false;
        }
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View attributeView = linearLayout.getChildAt(i);
            TextInputEditText attributeName = (TextInputEditText) attributeView.findViewById(R.id.et_attributeName);
            TextInputEditText attributeProperty = (TextInputEditText) attributeView.findViewById(R.id.et_attributeProperty);

            String attribute = attributeName.getText().toString();
            String property = attributeProperty.getText().toString();

            ProductAttribute productAttribute = new ProductAttribute();

            if (!attribute.equals("") && !property.equals("") && totalUnits > 1) {
                productAttribute.setItemId(0);
                productAttribute.setAttrName(attribute);
                productAttribute.setAttrProperty(property);
            } else {
                valuesCorrect = false;
            }
            productAttributes.add(productAttribute);
        }
        return valuesCorrect;
    }

    void insertProductAttributes(String productName, ArrayList<ProductAttribute> productAttributes) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                productId = Inventorydb.getDatabase(getApplicationContext()).dao().getLastProductId();
                for (int i = 0; i < productAttributes.size(); i++) {
                    ProductAttribute productAttribute = new ProductAttribute();
                    Log.d("LAST PRODUCT ID", String.valueOf(productId));
                    productAttribute.setItemId(productId);
                    productAttribute.setAttrName(productAttributes.get(i).getAttrName());
                    productAttribute.setAttrProperty(productAttributes.get(i).getAttrProperty());
                    myRepository.insert(productAttribute);
                }
            }
        };
        thread.start();
    }
}
