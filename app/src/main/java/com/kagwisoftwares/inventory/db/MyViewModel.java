package com.kagwisoftwares.inventory.db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kagwisoftwares.inventory.db.entities.Category;
import com.kagwisoftwares.inventory.db.entities.ProductItem;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;

import java.util.List;
 
public class MyViewModel extends AndroidViewModel {

    private MyRepository mRepository;

    private LiveData<List<ProductItem>> allProducts;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<StockCategoriesModel>> allStock;
    private LiveData<List<ProductItem>> allProductsById;


    public MyViewModel(Application application) {
        super(application);
        mRepository = new MyRepository(application);
        allProducts = mRepository.getAllProductItems();
        allCategories = mRepository.getAllCategories();
        allStock = mRepository.getAllStock();
    }

    public LiveData<List<ProductItem>> getAllProducts() {
        return allProducts;
    }

    public LiveData<List<ProductItem>> getAllProductsById(Application application, int itemId) {
        mRepository = new MyRepository(application, itemId);
        allProductsById = mRepository.getAllProductsById();
        return allProductsById;
    }

    public void insertProductItem(ProductItem item) {
        mRepository.insert(item);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insertCategory(Category category) {
        mRepository.insert(category);
    }

    public LiveData<List<StockCategoriesModel>> getAllStock() { return allStock; }
}
