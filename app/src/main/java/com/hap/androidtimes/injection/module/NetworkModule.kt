package com.hap.androidtimes.injection.module

import com.hap.androidtimes.network.Endpoint
import com.hap.androidtimes.network.api.TimesApi
import com.hap.androidtimes.network.service.TimesApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by luis on 5/16/18.
 */
@Module
class NetworkModule {
    private fun <T> getApiAdapter(okHttpClient: OkHttpClient, clazz: Class<T>): T {
        return Retrofit.Builder()
                .baseUrl(Endpoint.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideTimesApiService(okHttpClient: OkHttpClient): TimesApiService {
        val timesApi: TimesApi = getApiAdapter(okHttpClient, TimesApi::class.java)
        return TimesApiService(timesApi)
    }
}