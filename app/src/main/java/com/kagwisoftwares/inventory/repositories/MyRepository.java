package com.kagwisoftwares.inventory.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kagwisoftwares.inventory.Dao.Dao;
import com.kagwisoftwares.inventory.db.Inventorydb;
import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;

import java.util.List;

public class MyRepository {

    private Dao dao;
    private LiveData<List<Phone>> allPhones;
    private LiveData<List<Category>> allCategories;

    public MyRepository(Application application) {
        Inventorydb db = Inventorydb.getDatabase(application);
        dao = db.dao();
        allPhones = dao.getPhones();
        allCategories = dao.getCategories();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
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
