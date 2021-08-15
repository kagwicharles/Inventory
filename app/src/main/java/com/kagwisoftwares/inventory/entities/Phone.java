package com.kagwisoftwares.inventory.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Phone", foreignKeys = {@ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "categoryId",
        onDelete = ForeignKey.CASCADE)
})
public class Phone {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int phoneId;

    @NonNull
    @ColumnInfo(name = "categoryId", index = true)
    private int categoryId;

    @NonNull
    @ColumnInfo(name = "category")
    private String phoneCategory;

    @NonNull
    @ColumnInfo(name = "ram")
    private String phoneRam;

    @NonNull
    @ColumnInfo(name = "storage")
    private String phoneStorage;

    @ColumnInfo(name = "primary_camera")
    private String primaryCameraPixels;

    @ColumnInfo(name = "os_version")
    private String osVersion;

    @ColumnInfo(name = "total_units")
    private int phoneUnits;

    @ColumnInfo(name = "date_created")
    private Date date;

    public int getPhoneId() {
        return phoneId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getPhoneCategory() {
        return phoneCategory;
    }

    public String getPhoneRam() {
        return phoneRam;
    }

    public String getPhoneStorage() {
        return phoneStorage;
    }

    public String getPrimaryCameraPixels() {
        return primaryCameraPixels;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public int getPhoneUnits() {
        return phoneUnits;
    }

    public Date getDate() {
        return date;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public void setPhoneCategory(@NonNull String phoneCategory) {
        this.phoneCategory = phoneCategory;
    }

    public void setPhoneRam(String phoneRam) {
        this.phoneRam = phoneRam;
    }

    public void setPhoneStorage(String phoneStorage) {
        this.phoneStorage = phoneStorage;
    }

    public void setPrimaryCameraPixels(String primaryCameraPixels) {
        this.primaryCameraPixels = primaryCameraPixels;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setPhoneUnits(int phoneUnits) {
        this.phoneUnits = phoneUnits;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
