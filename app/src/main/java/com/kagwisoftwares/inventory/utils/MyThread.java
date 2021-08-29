package com.kagwisoftwares.inventory.utils;

import android.content.Context;

import com.kagwisoftwares.inventory.db.Inventorydb;

import java.util.concurrent.Callable;

public class MyThread implements Callable<String> {

    private final Context context;
    private final String item;

    public MyThread(String item, Context context) {
        this.context = context;
        this.item = item;
    }

    @Override
    public String call() {
        int itemId = Inventorydb.
                getDatabase(context.getApplicationContext()).dao().getCategory(item);
        int total = Inventorydb.
                getDatabase(context.getApplicationContext()).dao().getTotalStockByCategory(itemId);
        return String.valueOf(total);
    }
}