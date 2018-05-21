package com.hap.androidtimes.persistence.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hap.androidtimes.network.LoadingState
import com.hap.androidtimes.network.model.TimesResponse
import com.hap.androidtimes.network.service.TimesApiInterface
import com.hap.androidtimes.network.service.TimesApiService
import com.hap.androidtimes.persistence.entity.TimesEntity
import com.hap.androidtimes.persistence.source.TimesDataSource
import com.hap.androidtimes.ui.adapter.TimesAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by luis on 5/16/18.
 */
class TimesViewModel(private val timesDataSource: TimesDataSource, private val timesApiService: TimesApiService) : ViewModel(), TimesApiInterface {
    var loadingState: LoadingState = LoadingState.IDLE
    var timesLiveData: MutableLiveData<List<TimesEntity>> = MutableLiveData()
    val timesAdapter = TimesAdapter()

    override fun getTimesList(): LiveData<TimesResponse> = timesApiService.getTimesList()

    fun handleResponse(timesResponse: TimesResponse) {
        when {
            timesResponse.error != null || timesResponse.timesEntities == null -> {
                // we have an error, so we would need to try to load from local db, in case we have something, we display,
                // in case we don't have anything we show the error screen
                handleResponse {
                    loadingState = LoadingState.ERROR
                    val timeList: ArrayList<TimesEntity> = ArrayList()
                    val selectedList = timesDataSource.selectAll()
                    selectedList?.let {
                        timeList.addAll(selectedList)
                    }
                    notifyList(timeList)
                }
            }
            else -> {
                // this case is for when we have something coming from the network, we try to store all the information,
                // then we just notify to show the content, we mark this as COMPLETE, since we don't have pagination, otherwise we woud
                // return to IDLE, so we can fetch the next page later.
                handleResponse {
                    loadingState = LoadingState.COMPLETE
                    timesResponse.timesEntities?.let {
                        it.forEach {
                            timesDataSource.insert(it)
                        }
                        notifyList(it)
                    } ?: notifyList(ArrayList())
                }
            }
        }
    }

    private fun handleResponse(function: () -> Unit) {
        Observable.just(function)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    it()
                }
    }

    private fun notifyList(timesListResponse: ArrayList<TimesEntity>) {
        Observable.just(timesListResponse)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    timesLiveData.value = it
                }
    }
}