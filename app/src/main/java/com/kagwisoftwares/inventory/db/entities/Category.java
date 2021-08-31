package com.kagwisoftwares.inventory.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.Date;

@Entity(tableName = "category", indices = {@Index(value = {"category_name"}, unique = true)})
public class Category {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int categoryId;

    @NonNull
    @ColumnInfo(name = "category_name")
    private String category_name;

    @ColumnInfo(name = "date_created")
    private int date_created;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] category_image;

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory_name(@NonNull String category_name) {
        this.category_name = category_name;
    }

    public void setDate_created(int date_created) {
        this.date_created = date_created;
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

    public int getDate_created() {
        return date_created;
    }

    public byte[] getCategory_image() {
        return category_image;
    }

    public static Comparator<Category> CategoryNameAZComparator = new Comparator<Category>() {
        @Override
        public int compare(Category category, Category t1) {
            return category.getCategory_name().compareTo(t1.getCategory_name());
        }
    };

    public static Comparator<Category> CategoryNameZAComparator = new Comparator<Category>() {
        @Override
        public int compare(Category category, Category t1) {
            return t1.getCategory_name().compareTo(category.getCategory_name());
        }
    };

    public static Comparator<Category> CategoryDateAscendingComparator = new Comparator<Category>() {
        @Override
        public int compare(Category category, Category t1) {
            return category.getDate_created() - t1.getDate_created();
        }
    };

    public static Comparator<Category> CategoryDateDescendingComparator = new Comparator<Category>() {
        @Override
        public int compare(Category category, Category t1) {
            return t1.getDate_created() - category.getDate_created();
        }
    };
}
