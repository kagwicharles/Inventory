package com.kagwisoftwares.inventory.db;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductAttribute;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProductItem(ProductItem productItem);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProductAttribute(ProductAttribute productAttribute);

    @Query("DELETE FROM product_item")
    void deleteAllPhone();

    @Query("SELECT * FROM product_item ORDER BY item_name ASC")
    LiveData<List<ProductItem>> getProductItems();

    @Query("SELECT * FROM product_item WHERE categoryId=:id ORDER BY item_name ASC")
    LiveData<List<ProductItem>> getProductItemsById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Query("DELETE FROM category WHERE id=:categoryId")
    void deleteAllCategory(int categoryId);

    @Query("DELETE FROM product_item WHERE id=:productId")
    void deleteProductById(int productId);

    @Query("SELECT * FROM category ORDER BY category_name ASC")
    LiveData<List<Category>> getCategories();

    @Transaction
    @Query("SELECT * FROM category")
    LiveData<List<StockCategoriesModel>> getAllStock();

    @Query("SELECT id FROM category WHERE category_name=:name")
    int getCategory(String name);

    @Query("SELECT id FROM product_item WHERE item_name=:name")
    int getProductIdByName(String name);

    @Query("SELECT id FROM product_item ORDER BY id DESC LIMIT 1")
    int getLastProductId();

    @Query("SELECT id FROM category ORDER BY id DESC LIMIT 1")
    int getLastCategoryId();

    @Query("SELECT SUM(item_units) as sum_score FROM product_item;")
    int getTotalStockForShop();

    @Query("SELECT SUM(item_units) as sum_score FROM product_item WHERE categoryId=:categoryId;")
    int getTotalStockByCategory(int categoryId);

    @Query("SELECT SUM(item_units) as sum_score FROM product_item WHERE id=:productId;")
    int getTotalUnitsById(int productId);

    @Query("SELECT * FROM product_attribute WHERE itemId=:itemId;")
    List<ProductAttribute> getAllAttributesById(int itemId);

    @Query("UPDATE product_item SET item_units = :units WHERE id=:itemId;")
    int updateStock( int units, int itemId);
}
