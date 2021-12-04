package ir.mohsenafshar.apps.mytmdb.data.local

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import ir.mohsenafshar.apps.mytmdb.data.local.entity.Genre
import java.lang.reflect.Type


class DataConverter {
    @TypeConverter
    fun fromCountryLangList(countryLang: List<Genre?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toCountryLangList(countryLangString: String?): List<Genre>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson<List<Genre>>(countryLangString, type)
    }
    @TypeConverter
    fun fromGenreIdList(countryLang: List<Long?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Long?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toGenreIdList(countryLangString: String?): List<Long>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Long?>?>() {}.type
        return gson.fromJson<List<Long>>(countryLangString, type)
    }
}