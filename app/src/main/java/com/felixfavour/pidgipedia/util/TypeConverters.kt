package com.felixfavour.pidgipedia.util

import androidx.room.TypeConverter
import com.felixfavour.pidgipedia.entity.Word

class ListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }


}