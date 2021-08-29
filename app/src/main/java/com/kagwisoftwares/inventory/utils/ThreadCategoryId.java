package com.kagwisoftwares.inventory.utils;

import android.content.Context;

import com.kagwisoftwares.inventory.db.Inventorydb;

import java.util.concurrent.Callable;

public class ThreadCategoryId implements Callable<String> {

    private final Context context;
    private final String item;

    public ThreadCategoryId(String item, Context context) {
        this.context = context;
        this.item = item;
    }

    @Override
    public String call() {
        int categoryId = Inventorydb.
                getDatabase(context.getApplicationContext()).
                dao().getCategory(item);
        return String.valueOf(categoryId);
    }
}