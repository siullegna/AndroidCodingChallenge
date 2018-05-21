package com.hap.androidtimes.persistence.schema

/**
 * Created by luis on 5/16/18.
 */
object TimesInfo {
    const val TABLE_NAME = "times"

    object Columns {
        const val _ID = "_id"
        const val TITLE = "display_title"
        const val RATING = "mpaa_rating"
        const val REVIEWER = "byline"
        const val HEADLINE = "headline"
        const val SUMMARY_SHORT = "summary_short"
        const val PUBLICATION_DATE = "publication_date"
        const val MULTIMEDIA = "multimedia"
    }
}