package com.miklesam.openpunkapi.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(beer: Beer)

    @Delete
    fun delete(beer: Beer)

    @Query("SELECT* FROM beer_table WHERE id = :beerId")
    fun checkingWithId(beerId:String):LiveData<Beer>

    @Query("SELECT * FROM beer_table ")
    fun getAllBears(): LiveData<List<Beer>>
}