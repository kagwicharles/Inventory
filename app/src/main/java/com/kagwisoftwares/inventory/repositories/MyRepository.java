package com.kagwisoftwares.inventory.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kagwisoftwares.inventory.Dao.Dao;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.util.List;

public class MyRepository {

    private Dao dao;

    private LiveData<List<Phone>> allPhones;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<StockCategoriesModel>> allStock;

    public MyRepository(Application application) {
        Inventorydb db = Inventorydb.getDatabase(application);
        dao = db.dao();
        allPhones = dao.getPhones();
        allCategories = dao.getCategories();
        allStock = dao.getAllStock();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<StockCategoriesModel>> getAllStock() {
        return allStock;
    }

    public void insert(Phone phone) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertPhone(phone);
        });
    }

    public void insert(Category category) {
        Inventorydb.databaseWriteExecutor.execute(() -> {
            dao.insertCategory(category);
        });
    }
}
