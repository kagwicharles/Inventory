package com.kagwisoftwares.inventory.models;

public class ItemModel {

    private String itemName;
    private int itemTotal;

    public ItemModel(String itemName, int itemTotal) {
        this.itemName = itemName;
        this.itemTotal = itemTotal;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getItemTotal() {
        return this.itemTotal;
    }
}
