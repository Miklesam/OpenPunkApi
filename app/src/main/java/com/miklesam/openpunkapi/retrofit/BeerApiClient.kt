package com.miklesam.openpunkapi.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miklesam.openpunkapi.data.Beer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object BeerApiClient{
    private val TAG = "BeerApiClient"
    val mBeer = MutableLiveData<Beer>()
    val mBeers = MutableLiveData<List<Beer>>()
    val mError = MutableLiveData<String>()

    fun getBeer(): LiveData<Beer> {
        return mBeer
    }

    fun getBeerFood(): LiveData<List<Beer>> {
        return mBeers
    }

    fun getError(): LiveData<String> {
        return mError
    }
    fun getRandomBeer() {
        mError.value=null
        mBeer.value=null
        val compositeDisposable=CompositeDisposable()
        compositeDisposable.add(
            ServiceGenerator.RetrofitHolderApi.getRandomBeer()
                .timeout(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for (team in it) {
                        Log.w(TAG, it.get(0).toString())
                     mBeer.postValue(it.get(0))
                    }
                },{
                    Log.w(TAG, it.message.toString())
                    if (it is TimeoutException){
                        mError.postValue("action doesn't complete within the given time");
                    } else{
                        mError.postValue(it.message);
                    }
                   //compositeDisposable.dispose()
                }))

    }

    fun getBeerWithFood(page:String,per_page:String,category:String) {
        //mError.value=null
        mBeers.value=null
        val compositeDisposable=CompositeDisposable()
        compositeDisposable.add(
            ServiceGenerator.RetrofitHolderApi.beerWithFood(page,per_page,category)
                .delay(3,TimeUnit.SECONDS)
                .timeout(5,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mBeers.postValue(it)
                },{
                    Log.w(TAG, it.message.toString())
                    if (it is TimeoutException){
                        //mError.postValue("action doesn't complete within the given time");
                    } else{
                        //mError.postValue(it.message);
                    }
                    //compositeDisposable.dispose()
                }))

    }




}