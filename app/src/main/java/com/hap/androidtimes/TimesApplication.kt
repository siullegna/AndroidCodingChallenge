package com.hap.androidtimes

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.hap.androidtimes.injection.component.AppComponent
import com.hap.androidtimes.injection.component.DaggerAppComponent
import com.hap.androidtimes.injection.module.AppModule
import com.hap.androidtimes.injection.module.DatabaseModule
import com.hap.androidtimes.injection.module.NetworkModule

/**
 * Created by luis on 5/16/18.
 */
class TimesApplication : Application() {
    companion object {
        private lateinit var INSTANCE: TimesApplication

        fun getInstance(): TimesApplication = INSTANCE
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        TimesApplication.INSTANCE = this

        Fresco.initialize(this)

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .databaseModule(DatabaseModule())
                .networkModule(NetworkModule())
                .build()
    }

    fun appComponent(): AppComponent = appComponent

}