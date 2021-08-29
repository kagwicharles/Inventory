package com.kagwisoftwares.inventory.utils;

import android.content.Context;

import com.kagwisoftwares.inventory.db.Inventorydb;

import java.util.concurrent.Callable;

public class ThreadAllCategories implements Callable<String> {

    private final Context context;

    public ThreadAllCategories(Context context) {
        this.context = context;
    }

    @Override
    public String call() {
        int totalCategories = Inventorydb.
                getDatabase(context.getApplicationContext()).
                dao().getTotalCategories();
        return String.valueOf(totalCategories);
    }
}