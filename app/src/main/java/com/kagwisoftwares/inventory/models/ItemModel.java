package com.kagwisoftwares.inventory.models;

public class ItemModel {

    private String itemName;
    private int itemTotal;
    private double itemPercent;
    private int itemIcon;

    public ItemModel(String itemName, int itemTotal, double itemPercent, int itemIcon) {
        this.itemName = itemName;
        this.itemTotal = itemTotal;
        this.itemPercent = itemPercent;
        this.itemIcon = itemIcon;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getItemTotal() {
        return this.itemTotal;
    }

    public double getItemPercent() {
        return this.itemPercent;
    }

    public int getItemIcon() {
        return this.itemIcon;
    }
}
