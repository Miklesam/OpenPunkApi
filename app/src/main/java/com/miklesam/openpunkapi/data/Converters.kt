package com.codingwithmitch.foodrecipes.persistence

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): Array<String>? {
        val listType = object : TypeToken<Array<String>>() {

        }.type
        return Gson().fromJson<Array<String>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Array<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}