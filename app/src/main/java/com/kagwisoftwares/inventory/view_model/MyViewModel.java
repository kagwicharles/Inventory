package com.kagwisoftwares.inventory.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kagwisoftwares.inventory.entities.Category;
import com.kagwisoftwares.inventory.entities.Phone;
import com.kagwisoftwares.inventory.repositories.MyRepository;

import java.util.List;
 
public class MyViewModel extends AndroidViewModel {

    private MyRepository mRepository;

    private final LiveData<List<Phone>> allPhones;
    private final LiveData<List<Category>> allCategories;

    public MyViewModel(Application application) {
        super(application);
        mRepository = new MyRepository(application);
        allPhones = mRepository.getAllPhones();
        allCategories = mRepository.getAllCategories();
    }

    public LiveData<List<Phone>> getAllPhones() {
        return allPhones;
    }

    public void insertPhone(Phone phone) {
        mRepository.insert(phone);
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insertCategory(Category category) {
        mRepository.insert(category);
    }
}
