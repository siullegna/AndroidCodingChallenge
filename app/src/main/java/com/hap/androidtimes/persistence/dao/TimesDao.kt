package com.hap.androidtimes.persistence.dao

import android.arch.persistence.room.*
import com.hap.androidtimes.persistence.entity.TimesEntity
import com.hap.androidtimes.persistence.schema.TimesInfo

/**
 * Created by luis on 5/16/18.
 */
@Dao
interface TimesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timesEntity: TimesEntity)

    @Delete
    fun delete(timesEntity: TimesEntity)

    @Query("SELECT * " +
            "FROM ${TimesInfo.TABLE_NAME}")
    fun selectAll(): List<TimesEntity>?
}