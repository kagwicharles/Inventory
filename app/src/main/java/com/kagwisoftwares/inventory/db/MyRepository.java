package com.kagwisoftwares.inventory.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductAttribute;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.util.List;

public class MyRepository {

    private Dao dao;
    private int productId;

    private LiveData<List<ProductItem>> allProductItems;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<StockCategoriesModel>> allStock;
    private LiveData<List<ProductItem>> allProductsById;

    public MyRepository(Application application) {
        Inventorydb db = Inventorydb.getDatabase(application);
        dao = db.dao();
        allProductItems = dao.getProductItems();
        allCategories = dao.getCategories();
        allStock = dao.getAllStock();
    }

    public MyRepository(Application application, int productId) {
        Inventorydb db = Inventorydb.getDatabase(application);
        dao = db.dao();
        allProductsById = dao.getProductItemsById(productId);
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

    public LiveData<List<ProductItem>> getAllProductsById() {
        return allProductsById;
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

    public void insert(Category category) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertCategory(category);
        });
    }
}
