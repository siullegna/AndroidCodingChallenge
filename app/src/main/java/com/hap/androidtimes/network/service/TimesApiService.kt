package com.hap.androidtimes.network.service

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hap.androidtimes.network.api.TimesApi
import com.hap.androidtimes.network.model.TimesResponse
import com.hap.androidtimes.network.model.TimesSource
import com.hap.androidtimes.persistence.entity.TimesEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by luis on 5/16/18.
 */
class TimesApiService(private val timesApi: TimesApi) : TimesApiInterface {
    override fun getTimesList(): LiveData<TimesResponse> {
        val liveData: MutableLiveData<TimesResponse> = MutableLiveData()

        timesApi.getTimesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val timesEntities = TimesEntity.convert(it.results)
                    liveData.value = TimesResponse(timesEntities = timesEntities, timesSource = TimesSource.NETWORK)
                }, {
                    liveData.value = TimesResponse(error = it, timesSource = TimesSource.NETWORK)
                })

        return liveData
    }
}