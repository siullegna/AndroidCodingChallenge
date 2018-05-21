package com.hap.androidtimes.injection.component

import com.hap.androidtimes.injection.module.AppModule
import com.hap.androidtimes.injection.module.DatabaseModule
import com.hap.androidtimes.injection.module.NetworkModule
import com.hap.androidtimes.ui.BaseTimesActivity
import com.hap.androidtimes.ui.adapter.TimesDecoration
import dagger.Component
import javax.inject.Singleton

/**
 * Created by luis on 5/16/18.
 */
@Singleton
@Component(modules = [AppModule::class,
    NetworkModule::class,
    DatabaseModule::class])
interface AppComponent {
    fun inject(baseTimesActivity: BaseTimesActivity)
    fun inject(timesDecoration: TimesDecoration)
}