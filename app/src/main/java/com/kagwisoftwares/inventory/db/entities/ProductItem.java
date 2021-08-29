package com.kagwisoftwares.inventory.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "product_item", foreignKeys = {@ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE)
})
public class ProductItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int itemId;

    @NonNull
    @ColumnInfo(name = "categoryId", index = true)
    private int categoryId;

    @NonNull
    @ColumnInfo(name = "item_name")
    private String item_name;

    @NonNull
    @ColumnInfo(name = "item_units")
    private int item_units;

    @NonNull
    @ColumnInfo(name = "date_created")
    private Date date;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] item_image;

    public void setItemId(@NonNull int itemId) {
        this.itemId = itemId;
    }

    public void setItem_name(@NonNull String item_name) {
        this.item_name = item_name;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public void setItem_image(byte[] item_image) {
        this.item_image = item_image;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setItem_units(@NonNull int item_units) {
        this.item_units = item_units;
    }

    @NonNull
    public int getItem_units() {
        return item_units;
    }
    
    public int getCategoryId() {
        return categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    @NonNull
    public String getItem_name() {
        return item_name;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public byte[] getItem_image() {
        return item_image;
    }
}
