package com.miklesam.openpunkapi.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.openpunkapi.data.Beer

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {


    private var repository: FavoriteRepository =FavoriteRepository(application)
    private var allBeer: LiveData<List<Beer>> = repository.getBeers()
    private val beerView = MutableLiveData<Boolean>()
    private val choosenBeer = MutableLiveData<Beer>()
    fun getView(): LiveData<Boolean> = beerView
    fun getMyBeer(): LiveData<Beer> = choosenBeer

    fun getBeers(): LiveData<List<Beer>> {
        return allBeer
    }

    fun delete(beer: Beer){
        repository.delete(beer)
    }

    fun setView(boolean: Boolean){
        beerView.value=boolean
    }

    fun getbeerView():Boolean?{
        return beerView.value
    }

    fun setMyBeer(beer: Beer){
        choosenBeer.value=beer
    }

}