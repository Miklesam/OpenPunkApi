package com.miklesam.openpunkapi.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.retrofit.BeerApiClient

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: CategoryRepository = CategoryRepository(application)

    fun beerWithFood(page:String,per_page:String,category:String){
        return repository.beerWithFood(page,per_page,category)
    }

    fun getBeerFood(): LiveData<List<Beer>> {
        return repository.getBeerFood()
    }
}