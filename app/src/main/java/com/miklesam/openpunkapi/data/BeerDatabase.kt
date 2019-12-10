package com.miklesam.openpunkapi.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codingwithmitch.foodrecipes.persistence.Converters

@Database(entities = [Beer::class], version = 2)
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
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }

    }
    class PopulateDbAsyncTask(db: BeerDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.noteDao()
        override fun doInBackground(vararg p0: Unit?) {
            noteDao?.insert(Beer("title 1", "description 1","","",""))
            noteDao?.insert(Beer("title 2", "description 2","","",""))
            noteDao?.insert(Beer("title 3", "description 5","","",""))
        }
    }
  }