package com.kagwisoftwares.inventory.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;

import java.util.List;

public class StockCategoriesModel {

    @Embedded
    private Category category;

    @Relation(
            parentColumn = "id",
            entityColumn = "categoryId"
    )
    private List<Phone> phones;

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Phone> getPhones() {
        return phones;
    }
}
