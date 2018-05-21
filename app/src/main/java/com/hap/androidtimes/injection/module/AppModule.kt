package com.hap.androidtimes.injection.module

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by luis on 5/16/18.
 */
@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideAppContext(): Context = context.applicationContext

    @Provides
    @Singleton
    fun provideResources(): Resources = context.resources

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}