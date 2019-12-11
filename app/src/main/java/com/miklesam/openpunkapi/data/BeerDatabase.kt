package com.miklesam.openpunkapi.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codingwithmitch.foodrecipes.persistence.Converters

@Database(entities = [Beer::class], version = 3)
@TypeConverters(Converters::class)
abstract class BeerDatabase : RoomDatabase() {

    abstract fun noteDao(): BeerDao

    companion object {
        private var instance: BeerDatabase? = null

        fun getInstance(context: Context): BeerDatabase? {
            if (instance == null) {
                synchronized(BeerDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BeerDatabase::class.java, "beer_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }



    }

  }