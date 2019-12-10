package com.miklesam.openpunkapi.ui.random

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miklesam.openpunkapi.R
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.DownloadAndSaveImageTask
import com.miklesam.openpunkapi.retrofit.BeerApiClient
import com.miklesam.openpunkapi.retrofit.BeerApiClient.getRandomBeer
import com.miklesam.openpunkapi.ui.favorite.FavoriteRepository

class RandomViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: RandomRepository = RandomRepository(application)
    private val favorite = MutableLiveData<Boolean>()
    private val screenSaver = MutableLiveData<Boolean>()
    private val favoriteChoose = MutableLiveData<Boolean>()

    fun isFavorite(): LiveData<Boolean> = favorite
    fun isScreenSaver(): LiveData<Boolean> = screenSaver
    init {
        favorite.value=false
        favoriteChoose.value=false
        screenSaver.value=true
        }


    fun setFavorite(boolean: Boolean){
        Log.w("favoriteChoose ",favoriteChoose.value.toString())
        Log.w("favorite ",favorite.value.toString())
        if(!favoriteChoose.value!!){
            favorite.value=boolean
        }

    }

    fun setFavoriteChoose(boolean: Boolean){
        favoriteChoose.value=boolean
    }

    fun insertBeer(beer:Beer){
    repository.insert(beer)
    }

    fun setScreenSaver(boolean: Boolean){
    screenSaver.value=boolean
    }

    fun getRandomBeer(){
        repository.getRandomBeer()
    }

    fun getBeer(): LiveData<Beer> {
        return repository.getBeer()
    }


    fun getError(): LiveData<String> {
        return repository.getError()
    }

    fun isThisFavorite():Boolean?{
        return favorite.value
    }

    fun checkId(id:String):LiveData<Beer>{
      return  repository.checkId(id)
    }


}