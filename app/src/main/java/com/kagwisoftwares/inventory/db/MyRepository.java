package com.kagwisoftwares.inventory.db;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductAttribute;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;
import com.kagwisoftwares.inventory.ui.AddCategoryActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MyRepository {

    private Dao dao;
    private Application application;

    private LiveData<List<ProductItem>> allProductItems;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<StockCategoriesModel>> allStock;
    private LiveData<List<ProductItem>> allProductsById;

    public MyRepository(Application application) {
        this.application = application;
        Inventorydb db = Inventorydb.getDatabase(application);
        dao = db.dao();
        allProductItems = dao.getProductItems();
        allCategories = dao.getCategories();
        allStock = dao.getAllStock();
    }

    public MyRepository(Application application, int productId) {
        this.application = application;
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

    public boolean insert(ProductItem productItem) {
        Future<Boolean> result = Inventorydb.databaseWriteExecutor.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                try {
                    dao.insertProductItem(productItem);
                    return true;
                } catch (SQLiteConstraintException e) {
                    return false;
                }
            }
        });
        try {
            return result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void insert(ProductAttribute productAttribute) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertProductAttribute(productAttribute);
        });
    }

    public boolean insert(Category category) {
        Future<Boolean> result = Inventorydb.databaseWriteExecutor.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                try {
                    dao.insertCategory(category);
                    return true;
                } catch (SQLiteConstraintException e) {
                    return false;
                }
            }
        });
        try {
            return result.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
