package com.hap.androidtimes.persistence.source

import com.hap.androidtimes.persistence.AppDatabase
import com.hap.androidtimes.persistence.entity.TimesEntity

/**
 * Created by luis on 5/16/18.
 */
class TimesDataSourceImpl : TimesDataSource {
    private val timesDao = AppDatabase.getInstance().timesDao()

    override fun insert(timesEntity: TimesEntity) {
        timesDao.insert(timesEntity)
    }

    override fun delete(timesEntity: TimesEntity) {
        timesDao.delete(timesEntity)
    }

    override fun selectAll(): List<TimesEntity>? = timesDao.selectAll()
}