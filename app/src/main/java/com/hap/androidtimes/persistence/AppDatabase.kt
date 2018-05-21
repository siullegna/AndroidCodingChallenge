package com.hap.androidtimes.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.hap.androidtimes.persistence.AppDatabase.Companion.DB_VERSION
import com.hap.androidtimes.persistence.converter.MultimediaConverter
import com.hap.androidtimes.persistence.entity.TimesEntity
import android.arch.persistence.room.Room
import com.hap.androidtimes.TimesApplication
import com.hap.androidtimes.persistence.dao.TimesDao

/**
 * Created by luis on 5/16/18.
 */
@Database(entities = [TimesEntity::class], version = DB_VERSION, exportSchema = false)
@TypeConverters(MultimediaConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "times.db"

        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(TimesApplication.getInstance().applicationContext, AppDatabase::class.java, DB_NAME)
                        .build()
            }

            return INSTANCE!!
        }
    }

    abstract fun timesDao(): TimesDao
}