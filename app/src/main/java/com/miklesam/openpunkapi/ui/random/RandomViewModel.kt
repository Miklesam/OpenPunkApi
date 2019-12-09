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
    private val myBeer=MutableLiveData<Beer>()

    fun isFavorite(): LiveData<Boolean> = favorite
    init {
        favorite.value=false
        }


    fun setFavorite(boolean: Boolean){
        favorite.value=boolean
    }
/*
    fun setFavorite(beer: Beer){
        if(favorite.value==false){
            myBeer.value=beer
            repository.insert(beer)
        }else{

        }
        favorite.value= favorite.value!!.not()
    }

 */

    fun getRandomBeer(){
        repository.getRandomBeer()
    }

    fun getBeer(): LiveData<Beer> {
        return repository.getBeer()
    }


    fun getError(): LiveData<String> {
        return repository.getError()
    }

    fun wasFavorite():Boolean?{
        return favorite.value
    }

    fun checkId(id:String):LiveData<Beer>{
      return  repository.checkId(id)
    }


}