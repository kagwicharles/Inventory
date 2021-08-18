package com.kagwisoftwares.inventory.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.ProductItem;
import com.kagwisoftwares.inventory.models.StockCategoriesModel;
import com.kagwisoftwares.inventory.repositories.MyRepository;

import java.util.List;
 
public class MyViewModel extends AndroidViewModel {

    private MyRepository mRepository;

    private final LiveData<List<ProductItem>> allProducts;
    private final LiveData<List<Category>> allCategories;
    private final LiveData<List<StockCategoriesModel>> allStock;

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
