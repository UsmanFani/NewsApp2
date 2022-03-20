package com.example.newsapp.db

import androidx.room.TypeConverter
import com.example.newsapp.model.Source

class Converter {

    //converter is used to convert the type of Source data class to String so
    //use in Article data class because in Table only primitive data(string,int, etc..)
    //type can be saved
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    //converting back to Source data class
    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}