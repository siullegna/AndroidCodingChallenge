package com.hap.androidtimes.network.api

import com.hap.androidtimes.network.model.TimesApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by luis on 5/16/18.
 */
interface TimesApi {
    @GET("/svc/movies/v2/reviews/dvd-picks.json?order=by-date&api-key=b75da00e12d54774a2d362adddcc9bef")
    fun getTimesList(): Observable<TimesApiResponse>
}