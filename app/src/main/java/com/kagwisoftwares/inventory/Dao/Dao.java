package com.kagwisoftwares.inventory.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPhone(Phone phone);

    @Query("DELETE FROM phone_table")
    void deleteAllPhone();

    @Query("SELECT * FROM phone_table ORDER BY category ASC")
    LiveData<List<Phone>> getPhones();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Query("DELETE FROM category_table")
    void deleteAllCategory();

    @Query("SELECT * FROM category_table ORDER BY category_name ASC")
    LiveData<List<Category>> getCategories();
}
