package com.miklesam.openpunkapi.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklesam.openpunkapi.data.DeleteBeer
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao
import com.miklesam.openpunkapi.data.BeerDatabase


class FavoriteRepository(application: Application){
    private var beerDao: BeerDao
    private var allBeers: LiveData<List<Beer>>

    init {
        val database: BeerDatabase = BeerDatabase.getInstance(application.applicationContext)!!
        beerDao = database.noteDao()
        allBeers = beerDao.getAllBears()

    }

    fun getBeers(): LiveData<List<Beer>> {
        return allBeers
    }

    fun delete(beer: Beer) {
       DeleteBeer(beerDao).delete(beer)
       // compositeDisposable.dispose()
    }
}