package com.kagwisoftwares.inventory.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kagwisoftwares.inventory.Dao.Dao;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.ProductAttribute;
import com.kagwisoftwares.inventory.entities.ProductItem;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.util.List;

public class MyRepository {

    private Dao dao;
    private int productId;

    private LiveData<List<ProductItem>> allProductItems;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<StockCategoriesModel>> allStock;

    public MyRepository(Application application) {
        Inventorydb db = Inventorydb.getDatabase(application);
        dao = db.dao();
        allProductItems = dao.getProductItems();
        allCategories = dao.getCategories();
        allStock = dao.getAllStock();
    }

    public LiveData<List<ProductItem>> getAllProductItems() {
        return allProductItems;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<StockCategoriesModel>> getAllStock() {
        return allStock;
    }

    public void insert(ProductItem productItem) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertProductItem(productItem);
        });
    }

    public void insert(ProductAttribute productAttribute) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertProductAttribute(productAttribute);
        });
    }

    public int getProductIdByName(String productName) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            productId = dao.getProductIdByName(productName);
        });
        return productId;
    }

    public int getLastProductId() {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            productId = dao.getLastProductId();
        });
        return productId;
    }

    public void insert(Category category) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertCategory(category);
        });
    }
}
