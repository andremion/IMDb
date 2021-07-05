package com.andremion.imdb.data.local.util

import androidx.room.TypeConverter

private const val SEPARATOR = ","

class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun stringToList(value: String): List<String> {
        return value.split(SEPARATOR)
    }
}
