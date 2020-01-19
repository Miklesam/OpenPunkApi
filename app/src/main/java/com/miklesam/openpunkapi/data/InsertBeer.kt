package com.miklesam.openpunkapi.data

import android.os.AsyncTask
import android.util.Log
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InsertBeer(beerDao: BeerDao) {
    val beerDao = beerDao

    fun insert(beer: Beer) {
        val compositeDisposable = CompositeDisposable()
        val disposable= Completable.fromCallable {
            beerDao.insert(beer)//User Dao fun
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Log.w("Dao","OnSuccess")},{ Log.w("Dao","OnError")})
        compositeDisposable.add(disposable)
    }
}