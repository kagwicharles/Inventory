package com.kagwisoftwares.inventory.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone_table")
public class Phone {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int phoneId;

    @NonNull
    @ColumnInfo(name = "category")
    private String phoneCategory;

    @NonNull
    @ColumnInfo(name = "ram")
    private int phoneRam;

    @NonNull
    @ColumnInfo(name = "storage")
    private int phoneStorage;

    @ColumnInfo(name = "processor_type")
    private String phoneProcessorType;

    @ColumnInfo(name = "front_camera")
    private String phoneFrontCameraPixels;

    @ColumnInfo(name = "back_camera")
    private String phoneBackCameraPixels;

    @ColumnInfo(name = "android_version")
    private String phoneAndroidVersion;

    @ColumnInfo(name = "battery_capacity")
    private String phoneBatteryCapacity;

    @ColumnInfo(name = "total_units")
    private int phoneUnits;

    public int getPhoneId() {
        return phoneId;
    }
    public String getPhoneCategory() {
        return phoneCategory;
    }

    public int getPhoneRam() {
        return phoneRam;
    }

    public int getPhoneStorage() {
        return phoneStorage;
    }

    public String getPhoneProcessorType() {
        return phoneProcessorType;
    }

    public String getPhoneFrontCameraPixels() {
        return phoneFrontCameraPixels;
    }

    public String getPhoneBackCameraPixels() {
        return phoneBackCameraPixels;
    }

    public String getPhoneAndroidVersion() {
        return phoneAndroidVersion;
    }

    public String getPhoneBatteryCapacity() {
        return phoneBatteryCapacity;
    }

    public int getPhoneUnits() {
        return phoneUnits;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public void setPhoneCategory(@NonNull String phoneCategory) {
        this.phoneCategory = phoneCategory;
    }

    public void setPhoneRam(int phoneRam) {
        this.phoneRam = phoneRam;
    }

    public void setPhoneStorage(int phoneStorage) {
        this.phoneStorage = phoneStorage;
    }

    public void setPhoneProcessorType(String phoneProcessorType) {
        this.phoneProcessorType = phoneProcessorType;
    }

    public void setPhoneFrontCameraPixels(String phoneFrontCameraPixels) {
        this.phoneFrontCameraPixels = phoneFrontCameraPixels;
    }

    public void setPhoneBackCameraPixels(String phoneBackCameraPixels) {
        this.phoneBackCameraPixels = phoneBackCameraPixels;
    }

    public void setPhoneAndroidVersion(String phoneAndroidVersion) {
        this.phoneAndroidVersion = phoneAndroidVersion;
    }

    public void setPhoneBatteryCapacity(String phoneBatteryCapacity) {
        this.phoneBatteryCapacity = phoneBatteryCapacity;
    }

    public void setPhoneUnits(int phoneUnits) {
        this.phoneUnits = phoneUnits;
    }

}
