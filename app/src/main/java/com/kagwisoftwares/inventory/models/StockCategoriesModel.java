package com.kagwisoftwares.inventory.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductItem;

import java.util.List;

public class StockCategoriesModel {

    @Embedded
    private Category category;

    @Relation(
            parentColumn = "id",
            entityColumn = "categoryId"
    )
    private List<ProductItem> productItems;

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }
}
