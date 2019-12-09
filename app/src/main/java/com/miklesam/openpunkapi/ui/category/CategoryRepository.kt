package com.miklesam.openpunkapi.ui.category

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao
import com.miklesam.openpunkapi.data.BeerDatabase
import com.miklesam.openpunkapi.retrofit.BeerApiClient

class CategoryRepository(application: Application) {
    private var beerDao: BeerDao

    init {
        val database: BeerDatabase = BeerDatabase.getInstance(application.applicationContext)!!
        beerDao = database.noteDao()
    }

    fun getBeerFood(): LiveData<List<Beer>> {
        return BeerApiClient.getBeerFood()
    }

    fun beerWithFood(page:String,per_page:String,category:String){
        BeerApiClient.getBeerWithFood(page,per_page,category)
    }
}