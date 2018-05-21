package com.hap.androidtimes.injection.module

import com.hap.androidtimes.network.service.TimesApiService
import com.hap.androidtimes.persistence.source.TimesDataSourceImpl
import com.hap.androidtimes.persistence.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by luis on 5/16/18.
 */
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideTimesDataSourceImpl(): TimesDataSourceImpl = TimesDataSourceImpl()

    @Provides
    @Singleton
    fun provideViewModelFactory(timesDataSourceImpl: TimesDataSourceImpl, timesApiService: TimesApiService): ViewModelFactory = ViewModelFactory(timesDataSourceImpl, timesApiService)
}