package com.miklesam.openpunkapi.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.retrofit.BeerApiClient

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: CategoryRepository = CategoryRepository(application)
    private var loadingData: Boolean = false
    private var error: Boolean = false
    private var category: String= ""
    private val choosenBeer = MutableLiveData<Beer>()
    private val favorite = MutableLiveData<Boolean>()
    private val favoriteChoose = MutableLiveData<Boolean>()
    private val categoryViewInt = MutableLiveData<Int>()
    private val appBarString = MutableLiveData<String>()

    fun isWhatView(): LiveData<Int> = categoryViewInt
    fun isFavorite(): LiveData<Boolean> = favorite
    fun getMyBeer(): LiveData<Beer> = choosenBeer
    fun getMyBar(): LiveData<String> = appBarString

    init {
        favorite.value=false
        favoriteChoose.value=false
        categoryViewInt.value=0
        appBarString.value=""
        repository.clear()
    }

    fun setFavorite(boolean: Boolean){
        if(!favoriteChoose.value!!){
            favorite.value=boolean
        }
    }

    fun getErrorState():Boolean{
        return error
    }
    fun setErrorState(boolean: Boolean){
        error=boolean
    }

    fun getCategory():String{
        return category
    }
    fun setCategory(choosen:String){
        category=choosen
    }

    fun setAppBar(text:String){
        appBarString.value=text
    }

    fun isThisFavorite():Boolean?{
        return favorite.value
    }

    fun setFavoriteChoose(boolean: Boolean){
        favoriteChoose.value=boolean
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

    fun setcategoryViewInt(view: Int){
        categoryViewInt.value=view
    }
    fun getViewIntCategory(): Int? {
        return categoryViewInt.value
    }

    fun setMyBeer(beer: Beer){
        choosenBeer.value=beer
    }

    fun clear(){
        repository.clear()
    }

    fun insertBeer(beer:Beer){
        repository.insert(beer)
    }

    fun checkId(id:String):LiveData<Beer>{
        return  repository.checkId(id)
    }

    fun getError(): LiveData<String> {
        return repository.getError()
    }

}