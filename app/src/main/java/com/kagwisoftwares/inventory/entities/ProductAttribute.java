package com.kagwisoftwares.inventory.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_attribute", foreignKeys = {@ForeignKey(entity = ProductItem.class,
        parentColumns = "id",
        childColumns = "itemId",
        onDelete = ForeignKey.CASCADE)
})
public class ProductAttribute {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int attrId;

    @NonNull
    @ColumnInfo(name = "itemId", index = true)
    private int itemId;

    @NonNull
    @ColumnInfo(name = "category_name")
    private String attrName;

    @NonNull
    @ColumnInfo(name = "category_property")
    private String attrProperty;

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @NonNull
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(@NonNull String attrName) {
        this.attrName = attrName;
    }

    @NonNull
    public String getAttrProperty() {
        return attrProperty;
    }

    public void setAttrProperty(@NonNull String attrProperty) {
        this.attrProperty = attrProperty;
    }

}
