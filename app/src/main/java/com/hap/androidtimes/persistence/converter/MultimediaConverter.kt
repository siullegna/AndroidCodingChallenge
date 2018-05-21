package com.hap.androidtimes.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hap.androidtimes.network.model.Multimedia

/**
 * Created by luis on 5/16/18.
 */
class MultimediaConverter {
    private val gSon = Gson()
    @TypeConverter
    fun fromString(multimedia: String?): Multimedia? {
        val listType = object : TypeToken<Multimedia>() {}.type
        return gSon.fromJson(multimedia, listType)
    }

    @TypeConverter
    fun fromMultimedia(multimedia: Multimedia?): String? {
        return gSon.toJson(multimedia)
    }
}