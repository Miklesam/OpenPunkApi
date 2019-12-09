package com.miklesam.openpunkapi.async

import android.os.AsyncTask
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao

class InsertBeerAsyncTask(beerDao: BeerDao) : AsyncTask<Beer, Unit, Unit>() {
    val beerDao = beerDao

    override fun doInBackground(vararg p0: Beer?) {
        beerDao.insert(p0[0]!!)
    }
}