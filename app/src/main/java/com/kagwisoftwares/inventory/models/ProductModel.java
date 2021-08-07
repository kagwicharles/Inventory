package com.kagwisoftwares.inventory.models;

public class ProductModel {

    private final String productName;
    private final int productIcon;
    private final int arrowRight;
    private final int productTotal;

    public ProductModel(String productName, int productIcon, int arrowRight, int productTotal) {
        this.productName = productName;
        this.productIcon = productIcon;
        this.arrowRight  = arrowRight;
        this.productTotal = productTotal;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getProductIcon() {
        return this.productIcon;
    }

    public int getArrowRight() {
        return this.arrowRight;
    }

    public int getProductTotal() {
        return this.productTotal;
    }
}
