package com.kagwisoftwares.inventory.utils;

import android.content.Context;

import com.kagwisoftwares.inventory.db.Inventorydb;

import java.util.concurrent.Callable;

public class ThreadProductTotalById implements Callable<String> {

    private final Context context;
    private final String item;

    public ThreadProductTotalById(String item, Context context) {
        this.context = context;
        this.item = item;
    }

    @Override
    public String call() {
        int productId = Inventorydb.
                getDatabase(context.getApplicationContext()).
                dao().getProductIdByName(item);
        int currentStock = Inventorydb.
                getDatabase(context.getApplicationContext()).
                dao().getTotalUnitsById(productId);
        return String.valueOf(currentStock);
    }
}