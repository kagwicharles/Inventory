package com.kagwisoftwares.inventory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.ProductAttribute;
import com.kagwisoftwares.inventory.entities.ProductItem;
import com.kagwisoftwares.inventory.repositories.MyRepository;
import com.kagwisoftwares.inventory.viewmodels.MyViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddProductItemActivity extends AppCompatActivity {

    private AutoCompleteTextView categories;
    private Button saveItem, addAttribute;
    private LinearLayout linearLayout;
    private TextInputEditText et_productName;

    private MyViewModel myViewModel;
    private String category, productName;
    private int productId;
    private ArrayList<ProductAttribute> productAttributes;
    private MyRepository myRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new MyTransitions().animateFade(this);
        setContentView(R.layout.activity_add_product_item);
        getSupportActionBar().setTitle("New Shipment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        saveItem = findViewById(R.id.saveItem);
        addAttribute = findViewById(R.id.add_attribute);
        categories = findViewById(R.id.categoryAutoComplete);
        linearLayout = findViewById(R.id.product_attributes);
        et_productName = findViewById(R.id.et_productName);

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
//        TextInputEditText et_attributeName = (TextInputEditText) attributeView.findViewById(R.id.et_attributeName);
//        TextInputEditText et_attributeProperty = (TextInputEditText) attributeView.findViewById(R.id.et_attributeProperty);
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

//            if (!attribute.equals("") || !property.equals("")) {
//                productAttribute.setItemId(itemId);
//                productAttribute.setAttrName(attribute);
//                productAttribute.setAttrProperty(property);
//                new MyRepository(this.getApplication()).insert(productAttribute);
//            } else {
//                valuesCorrect = false;
//            }

            if (!attribute.equals("") && !property.equals("")) {
                productAttributes.add(productAttribute);
                productAttribute.setItemId(0);
                productAttribute.setAttrName(attribute);
                productAttribute.setAttrProperty(property);
            } else {
                valuesCorrect = false;
            }
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
