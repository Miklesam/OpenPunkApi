package com.miklesam.openpunkapi.async

import android.os.AsyncTask
import android.util.Log
import com.miklesam.openpunkapi.data.Beer
import com.miklesam.openpunkapi.data.BeerDao

class DeleteBeerAsyncTask(beerDao: BeerDao) : AsyncTask<Beer, Unit, Unit>() {
    val beerDao = beerDao

    override fun doInBackground(vararg p0: Beer?) {
        beerDao.delete(p0[0]!!)
    }
}