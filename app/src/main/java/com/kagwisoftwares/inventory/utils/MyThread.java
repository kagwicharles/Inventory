package com.kagwisoftwares.inventory.utils;

import android.content.Context;
import android.util.Log;

import com.kagwisoftwares.inventory.db.Inventorydb;

public class MyThread extends Thread{

    private Context context;
    private int param, returnValue;

    public void setReturnValue(int value) {
        returnValue = value;
    }
    public int getReturnValue() {
        return returnValue;
    }

    public MyThread(Context context, int param) {
        this.context = context;
        this.param = param;
        super.start();
    }

    @Override
    public void run() {
        returnValue = Inventorydb.
                getDatabase(context).dao().getTotalStockByCategory(param);
        setReturnValue(returnValue);
    }
}
