package com.miklesam.openpunkapi.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miklesam.openpunkapi.data.Beer
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object BeerApiClient{
    private val TAG = "BeerApiClient"
    val mBeer = MutableLiveData<Beer>()
    val mBeers = MutableLiveData<List<Beer>>()
    val mCategoryError = MutableLiveData<String>()
    val mError = MutableLiveData<String>()
    var mBeerPagination=ArrayList<Beer>()

    fun getBeer(): LiveData<Beer> {
        return mBeer
    }
    fun clearBeers() {
        mBeers.value=null
    }

    fun clearCategoryError(){
        mCategoryError.value=null
    }

    fun getBeerFood(): LiveData<List<Beer>> {
        return mBeers
    }

    fun getError(): LiveData<String> {
        return mError
    }
    fun getCategoryError(): LiveData<String> {
        return mCategoryError
    }

    fun getRandomBeer() {
        mError.value=null
        mBeer.value=null
        val compositeDisposable=CompositeDisposable()
        compositeDisposable.add(
            ServiceGenerator.RetrofitHolderApi.getRandomBeer()
                .timeout(4,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for (team in it) {
                        Log.w(TAG, it.get(0).toString())
                     mBeer.postValue(it.get(0))
                    }
                },{
                    if (it is TimeoutException){
                        mError.postValue("action doesn't complete within the given time");
                        compositeDisposable.dispose()

                    } else{
                        mError.postValue(it.message);
                        Log.w(TAG, it.message.toString())
                    }

                }))

    }

    fun getBeerWithFood(page:Int,per_page:String,category:String) {
        mCategoryError.value=null
        mBeers.value=null
        val compositeDisposable=CompositeDisposable()
        compositeDisposable.add(
            ServiceGenerator.RetrofitHolderApi.beerWithFood(page.toString(),per_page,category)
                .delay(1,TimeUnit.SECONDS)
                .timeout(4,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(page==1){
                        mBeerPagination.clear()
                        mBeerPagination.addAll(it)
                        mBeers.postValue(it)
                    }else{
                     mBeerPagination.addAll(it)
                     mBeers.postValue(mBeerPagination)
                    }

                },{
                    Log.w(TAG, it.message.toString())
                    if (it is TimeoutException){
                        mCategoryError.postValue("action doesn't complete within the given time");
                    } else{
                         mCategoryError.postValue(it.message);
                    }
                    compositeDisposable.dispose()
                }))

    }




}