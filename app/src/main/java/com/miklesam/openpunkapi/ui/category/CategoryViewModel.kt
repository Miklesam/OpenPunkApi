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

    private val displayCategory = MutableLiveData<Boolean>()
    private var loadingData: Boolean = false

    private val viewingBeer = MutableLiveData<Boolean>()
    private val choosenBeer = MutableLiveData<Beer>()


    fun isCategoryDisplay(): LiveData<Boolean> = displayCategory
    fun isBeerViewing(): LiveData<Boolean> = viewingBeer
    fun getMyBeer(): LiveData<Beer> = choosenBeer

    init {
        displayCategory.value=true
    }

    fun setCategory(boolean: Boolean){
        displayCategory.value=boolean
    }

    fun isCategory(): Boolean? {
        return displayCategory.value
    }

    fun beerWithFood(page:Int,per_page:String,category:String){
        return repository.beerWithFood(page,per_page,category)
    }

    fun getBeerFood(): LiveData<List<Beer>> {
        return repository.getBeerFood()
    }

    fun searchNextPage(){
    if(!loadingData
        &&!isExhausted().value!!
    ){
        repository.searchNextPage()
    }
    }

    fun setLoading(boolean: Boolean){
        loadingData=boolean
    }
    fun isExhausted():LiveData<Boolean>{
        return repository.getExhausted()
    }

    fun setBeerView(boolean: Boolean){
        viewingBeer.value=boolean
    }

    fun setMyBeer(beer: Beer){
        choosenBeer.value=beer
    }

}