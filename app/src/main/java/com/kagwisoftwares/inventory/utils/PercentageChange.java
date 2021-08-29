package com.kagwisoftwares.inventory.utils;

public class PercentageChange {

    private Double percentChange;

    public PercentageChange(int newStock, int originalStock) {
        if (newStock > originalStock)
            percentChange = calculatePercentIncrease(newStock, originalStock);
        if (originalStock > newStock)
            percentChange = calculatePercentDecrease(newStock, originalStock);
    }

    Double calculatePercentIncrease(int newValue, int originalValue) {
        int increase = newValue - originalValue;
        return (double) ((increase/originalValue)*100);
    }

    Double calculatePercentDecrease(int newValue, int originalValue) {
        int decrease = originalValue - newValue;
        return (double) ((decrease/originalValue)*100);
    }

    public Double getPercentChange() {
        return percentChange;
    }

}
