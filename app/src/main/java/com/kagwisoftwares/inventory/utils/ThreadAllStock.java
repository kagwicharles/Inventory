package com.kagwisoftwares.inventory.utils;

import android.content.Context;

import com.kagwisoftwares.inventory.db.Inventorydb;

import java.util.concurrent.Callable;

public class ThreadAllStock implements Callable<String> {

    private final Context context;

    public ThreadAllStock(Context context) {
        this.context = context;
    }

    @Override
    public String call() {
        int totalStockForShop = Inventorydb.
                getDatabase(context.getApplicationContext()).
                dao().getTotalStockForShop();
        return String.valueOf(totalStockForShop);
    }
}