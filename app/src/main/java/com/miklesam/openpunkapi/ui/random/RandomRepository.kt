package com.miklesam.openpunkapi.ui.random

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.openpunkapi.async.InsertBeerAsyncTask
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao
import com.miklesam.openpunkapi.data.BeerDatabase
import com.miklesam.openpunkapi.retrofit.BeerApiClient

class RandomRepository(application: Application){

    private var beerDao: BeerDao

    init {
        val database: BeerDatabase = BeerDatabase.getInstance(application.applicationContext)!!
        beerDao = database.noteDao()
    }

    fun insert(beer: Beer) {
        val insertNoteAsyncTask = InsertBeerAsyncTask(beerDao).execute(beer)
    }

    fun getRandomBeer() {
        BeerApiClient.getRandomBeer()
    }

    fun getBeer(): LiveData<Beer> {
        return BeerApiClient.getBeer()
    }
    fun getError(): LiveData<String> {
        return BeerApiClient.getError()
    }

    fun checkId(id:String): LiveData<Beer> {
       return beerDao.checkingWithId(id)
    }

    fun clear(){
        BeerApiClient.clearBeer()
    }


}