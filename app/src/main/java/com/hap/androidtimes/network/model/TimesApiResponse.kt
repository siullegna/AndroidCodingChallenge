package com.hap.androidtimes.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.hap.androidtimes.persistence.converter.MultimediaConverter
import com.hap.androidtimes.persistence.entity.TimesEntity
import kotlinx.android.parcel.Parcelize
import kotlin.collections.ArrayList

/**
 * Created by luis on 5/16/18.
 */
data class TimesApiResponse(@SerializedName("has_more") val hasMore: Boolean = false,
                            @SerializedName("num_results") val numResults: Int = 0,
                            val results: ArrayList<TimesItem>? = null)

@Parcelize
data class TimesItem(@SerializedName("display_title") var title: String? = null,
                     @SerializedName("mpaa_rating") var rating: String? = null,
                     @SerializedName("byline") var reviewer: String? = null,
                     @SerializedName("headline") var headline: String? = null,
                     @SerializedName("summary_short") var summary: String? = null,
                     @SerializedName("publication_date") var publicationDate: String? = null,
                     @SerializedName("multimedia") var multimedia: Multimedia? = null) : Parcelable {
    companion object {
        fun convert(timesEntity: TimesEntity): TimesItem {
            val timesItem = TimesItem()

            timesItem.title = timesEntity.title
            timesItem.rating = timesEntity.rating
            timesItem.reviewer = timesEntity.reviewer
            timesItem.headline = timesEntity.headline
            timesItem.summary = timesEntity.summary
            timesItem.publicationDate = timesEntity.publicationDate
            timesItem.multimedia = MultimediaConverter().fromString(timesEntity.multimedia)

            return timesItem
        }
    }
}

@Parcelize
data class Multimedia(@SerializedName("src") val imageUrl: String? = null,
                      val width: Int = 0,
                      val height: Int = 0) : Parcelable

data class TimesResponse(var timesEntities: ArrayList<TimesEntity>? = null, var error: Throwable? = null, var timesSource: TimesSource? = null)

enum class TimesSource(private val source: String) {
    LOCAL_DB("LOCAL_DB"),
    NETWORK("NETWORK");

    companion object {
        fun fromSource(source: String): TimesSource = if (NETWORK.source == source) {
            NETWORK
        } else {
            LOCAL_DB
        }
    }
}