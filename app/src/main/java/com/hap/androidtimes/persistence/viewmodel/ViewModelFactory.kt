package com.hap.androidtimes.persistence.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hap.androidtimes.network.service.TimesApiService
import com.hap.androidtimes.persistence.source.TimesDataSourceImpl

/**
 * Created by luis on 5/16/18.
 */
class ViewModelFactory(private val timesDataSourceImpl: TimesDataSourceImpl, private val timesApiService: TimesApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimesViewModel::class.java)) {
            return TimesViewModel(timesDataSourceImpl, timesApiService) as (T)
        }

        throw IllegalArgumentException("Unknown {$modelClass} ViewModel class")
    }
}