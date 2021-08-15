package com.kagwisoftwares.inventory.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "other_electronics")
public class OtherElectronics {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int itemId;

    @NonNull
    @ColumnInfo(name = "item_name")
    private String item_name;

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
