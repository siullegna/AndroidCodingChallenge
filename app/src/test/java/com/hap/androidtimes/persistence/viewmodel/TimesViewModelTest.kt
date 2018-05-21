package com.hap.androidtimes.persistence.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.hap.androidtimes.network.api.TimesApi
import com.hap.androidtimes.network.model.TimesResponse
import com.hap.androidtimes.network.service.TimesApiService
import com.hap.androidtimes.persistence.entity.TimesEntity
import com.hap.androidtimes.persistence.source.TimesDataSource
import com.hap.androidtimes.ui.AppInterface
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.collections.ArrayList

/**
 * Created by luis on 5/20/18.
 */
class TimesViewModelTest {
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val appInterface = mock<AppInterface>()
    private val timesDataSource = mock<TimesDataSource>()
    private val timesApi = mock<TimesApi>()
    private lateinit var timesViewModel: TimesViewModel
    private lateinit var testScheduler: TestScheduler

    @Before
    fun setup() {
        val timesService = TimesApiService(timesApi)
        timesViewModel = TimesViewModel(timesDataSource, timesService)
        testScheduler = TestScheduler()
    }

    @Test
    fun loadTimes() {
        val liveData: MutableLiveData<TimesResponse> = MutableLiveData()
        liveData.value = getTimes(5)
        whenever(timesViewModel.getTimesList()).thenReturn(liveData)

        timesViewModel.getTimesList()
        verify(appInterface).showLoader()
        testScheduler.triggerActions()

        verify(appInterface).showContent()
    }

    @Test
    fun loadEmptyScreen() {
        val liveData: MutableLiveData<TimesResponse> = MutableLiveData()
        liveData.value = getTimes()
        whenever(timesViewModel.getTimesList()).thenReturn(liveData)

        timesViewModel.getTimesList()
        verify(appInterface).showLoader()

        testScheduler.triggerActions()

        verify(appInterface).showEmptyScreen()
    }

    @Test
    fun loadError() {
        val liveData: MutableLiveData<TimesResponse> = MutableLiveData()
        liveData.value = getTimes(-1)
        whenever(timesViewModel.getTimesList()).thenReturn(liveData)

        timesViewModel.getTimesList()
        verify(appInterface).showLoader()

        testScheduler.triggerActions()

        verify(appInterface).showEmptyScreen()
    }

    private fun getTimes(size: Int = 0): TimesResponse {
        val timesResponse = TimesResponse()

        when (size) {
            -1 -> {
                timesResponse.error = Throwable()
            }
            0 -> {
                timesResponse.timesEntities = ArrayList()
            }
            else -> {
                val timesList: ArrayList<TimesEntity> = ArrayList()
                for (index in 0..size) {
                    timesList.add(TimesEntity("title $index", "rating $index", "reviewer: $index", "headline $index",
                            "summary $index", "publication date $index", "\"multimedia\":{\n" +
                            "            \"type\":\"mediumThreeByTwo210\",\n" +
                            "            \"src\":\"https:\\/\\/static01.nyt.com\\/images\\/2018\\/05\\/18\\/arts\\/18firstreformed\\/18firstreformed-mediumThreeByTwo210.jpg\",\n" +
                            "            \"width\":210,\n" +
                            "            \"height\":140\n" +
                            "         }"))
                }
                timesResponse.timesEntities = timesList
            }
        }

        return timesResponse
    }

    inner class RxImmediateSchedulerRule : TestRule {
        override fun apply(base: Statement?, description: Description?): Statement {
            return object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxJavaPlugins.setIoSchedulerHandler { testScheduler }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                    try {
                        base?.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        RxAndroidPlugins.reset()
                    }
                }
            }
        }
    }
}