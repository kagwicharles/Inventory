package com.kagwisoftwares.inventory.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kagwisoftwares.inventory.Dao.Dao;
import com.kagwisoftwares.inventory.converters.Converters;
import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.ProductAttribute;
import com.kagwisoftwares.inventory.entities.ProductItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ProductAttribute.class, Category.class, ProductItem.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class Inventorydb extends RoomDatabase {

    public abstract Dao dao();

    private static volatile Inventorydb INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Inventorydb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Inventorydb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Inventorydb.class, "inventory_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}