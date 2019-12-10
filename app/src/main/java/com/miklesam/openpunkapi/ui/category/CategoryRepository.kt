package com.miklesam.openpunkapi.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao
import com.miklesam.openpunkapi.data.BeerDatabase
import com.miklesam.openpunkapi.retrofit.BeerApiClient

class CategoryRepository(application: Application) {
    private var beerDao: BeerDao
    private  var Page: Int = 0
    private  var preListSize: Int = 0
    private lateinit var Per_Page: String
    private lateinit var Category: String

    private val mIsQueryExhausted = MutableLiveData<Boolean>()
    private val mBeers = MediatorLiveData<List<Beer>>()

    init {
        val database: BeerDatabase = BeerDatabase.getInstance(application.applicationContext)!!
        beerDao = database.noteDao()
        initMediators()
    }

    fun getBeerFood(): LiveData<List<Beer>> {
        return mBeers
    }

    fun initMediators(){
        val beerList = BeerApiClient.getBeerFood()
        mBeers.addSource(beerList,
            Observer<List<Beer>> { beers ->
                if (beers != null) {
                    mBeers.setValue(beers)
                    doneQuery(beers)
                }

            })
    }


    fun doneQuery(beerList:List<Beer>){
           if(beerList.size%5!=0){
               mIsQueryExhausted.value=true
           }else{
               if(preListSize==beerList.size){
                   Log.w("Exhausted Too ",beerList.size.toString())
                   mIsQueryExhausted.value=true
               }

           }
        preListSize=beerList.size

    }

    fun getExhausted():LiveData<Boolean>{
        return  mIsQueryExhausted
    }


    fun beerWithFood(page:Int,per_page:String,category:String){
        Page=page
        Per_Page=per_page
        Category=category
        mIsQueryExhausted.value=false
        BeerApiClient.getBeerWithFood(page,per_page,category)
    }

    fun searchNextPage(){
        beerWithFood(Page+1,Per_Page,Category)
    }
}