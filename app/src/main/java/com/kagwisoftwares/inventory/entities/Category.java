package com.kagwisoftwares.inventory.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int categoryId;

    @NonNull
    @ColumnInfo(name = "category_name")
    private String category_name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] category_image;

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory_name(@NonNull String category_name) {
        this.category_name = category_name;
    }

    public void setCategory_image(byte[] category_image) {
        this.category_image = category_image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory_name() {
        return category_name;
    }

    public byte[] getCategory_image() {
        return category_image;
    }
}
