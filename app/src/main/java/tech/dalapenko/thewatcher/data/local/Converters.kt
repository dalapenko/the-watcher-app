package tech.dalapenko.thewatcher.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return Gson().fromJson(value, Array<Int>::class.java).toList()
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return Gson().toJson(list)
    }
}