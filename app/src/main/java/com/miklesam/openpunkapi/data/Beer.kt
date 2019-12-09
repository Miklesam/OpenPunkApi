package com.miklesam.openpunkapi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer_table")
data class Beer(
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "description")
    var description:String,
    @ColumnInfo(name = "image_url")
    var image_url:String,
    @ColumnInfo(name = "tagline")
    var tagline:String,
    @ColumnInfo(name = "id")
    var id :String
)
{
@PrimaryKey(autoGenerate = true)
    var id_key:Int=0
}