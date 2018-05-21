package com.hap.androidtimes.network.service

import android.arch.lifecycle.LiveData
import com.hap.androidtimes.network.model.TimesResponse

/**
 * Created by luis on 5/19/18.
 */
interface TimesApiInterface {
    fun getTimesList(): LiveData<TimesResponse>
}