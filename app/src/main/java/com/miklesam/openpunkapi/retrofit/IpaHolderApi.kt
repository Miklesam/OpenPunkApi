package com.miklesam.openpunkapi.retrofit

import com.miklesam.openpunkapi.data.Beer
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IpaHolderApi {
    @GET("beers/random")
    fun getRandomBeer(): Observable<List<Beer>>

    @GET("beers/")
    fun beerWithFood(
        @Query("page") page: String,
        @Query("per_page") per_page: String,
        @Query("food") food: String
    ): Single<List<Beer>>
}