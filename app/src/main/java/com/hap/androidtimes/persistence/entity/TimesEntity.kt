package com.hap.androidtimes.persistence.entity

import android.arch.persistence.room.*
import com.hap.androidtimes.network.model.TimesItem
import com.hap.androidtimes.persistence.converter.MultimediaConverter
import com.hap.androidtimes.persistence.schema.TimesInfo
import kotlin.collections.ArrayList

/**
 * Created by luis on 5/16/18.
 */
@Entity(tableName = TimesInfo.TABLE_NAME,
        indices = [Index(TimesInfo.Columns.TITLE, TimesInfo.Columns.REVIEWER, TimesInfo.Columns.PUBLICATION_DATE,
                unique = true)])
class TimesEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TimesInfo.Columns._ID)
    var id: Int = 0
    @ColumnInfo(name = TimesInfo.Columns.TITLE)
    var title: String? = null
    @ColumnInfo(name = TimesInfo.Columns.RATING)
    var rating: String? = null
    @ColumnInfo(name = TimesInfo.Columns.REVIEWER)
    var reviewer: String? = null
    @ColumnInfo(name = TimesInfo.Columns.HEADLINE)
    var headline: String? = null
    @ColumnInfo(name = TimesInfo.Columns.SUMMARY_SHORT)
    var summary: String? = null
    @ColumnInfo(name = TimesInfo.Columns.PUBLICATION_DATE)
    var publicationDate: String? = null
    @ColumnInfo(name = TimesInfo.Columns.MULTIMEDIA)
    var multimedia: String? = null

    constructor()

    @Ignore
    constructor(title: String?, rating: String, reviewer: String?, headline: String?, summary: String?, publicationDate: String?, multimedia: String?) {
        this.title = title
        this.rating = rating
        this.reviewer = reviewer
        this.headline = headline
        this.summary = summary
        this.publicationDate = publicationDate
        this.multimedia = multimedia
    }

    companion object {
        fun convert(timesItems: ArrayList<TimesItem>?): ArrayList<TimesEntity> {
            val timesEntities: ArrayList<TimesEntity> = ArrayList()

            timesItems?.let {
                it.forEach {
                    timesEntities.add(convert(it))
                }
            }

            return timesEntities
        }

        private fun convert(timesItem: TimesItem): TimesEntity {
            val timesEntity = TimesEntity()

            timesEntity.title = timesItem.title
            timesEntity.rating = timesItem.rating
            timesEntity.reviewer = timesItem.reviewer
            timesEntity.headline = timesItem.headline
            timesEntity.summary = timesItem.summary
            timesEntity.publicationDate = timesItem.publicationDate
            timesEntity.multimedia = MultimediaConverter().fromMultimedia(timesItem.multimedia)

            return timesEntity
        }
    }
}