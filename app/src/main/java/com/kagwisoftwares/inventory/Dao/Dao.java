package com.kagwisoftwares.inventory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPhone(Phone phone);

    @Query("DELETE FROM phone")
    void deleteAllPhone();

    @Query("SELECT * FROM phone ORDER BY category ASC")
    LiveData<List<Phone>> getPhones();

    @Query("SELECT * FROM phone WHERE categoryId=:id ORDER BY category ASC")
    List<Phone> getPhonesById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Query("DELETE FROM category WHERE id=:categoryId")
    void deleteAllCategory(int categoryId);

    @Query("SELECT * FROM category ORDER BY category_name ASC")
    LiveData<List<Category>> getCategories();

    @Transaction
    @Query("SELECT * FROM category")
    LiveData<List<StockCategoriesModel>> getAllStock();

    @Query("SELECT id FROM category WHERE category_name=:name")
    int getCategory(String name);
}
