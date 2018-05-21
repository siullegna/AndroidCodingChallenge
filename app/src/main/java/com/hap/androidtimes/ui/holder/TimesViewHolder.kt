package com.hap.androidtimes.ui.holder

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.hap.androidtimes.R
import com.hap.androidtimes.network.model.Multimedia
import com.hap.androidtimes.persistence.converter.MultimediaConverter
import com.hap.androidtimes.persistence.entity.TimesEntity
import com.hap.androidtimes.ui.adapter.TimesAdapter
import com.hap.androidtimes.ui.widget.TimesDraweeView

/**
 * Created by luis on 5/19/18.
 */
class TimesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    private val timesPhotoContainer: View? = itemView?.findViewById(R.id.times_photo_container)
    private val timesDraweeView: TimesDraweeView? = itemView?.findViewById(R.id.times_drawee_view)
    private val timesLoader: ProgressBar? = itemView?.findViewById(R.id.times_loader)
    private val timesReviewer: AppCompatTextView? = itemView?.findViewById(R.id.times_reviewer)
    private val timesTitle: AppCompatTextView? = itemView?.findViewById(R.id.times_title)
    private var isPhotoLoaded = false

    fun setupView(timesEntity: TimesEntity, headerSize: Int, onItemClickListener: TimesAdapter.OnItemClickListener? = null) {
        if (isPhotoLoaded) {
            timesLoader?.visibility = View.GONE
        } else {
            timesLoader?.visibility = View.VISIBLE
        }

        timesPhotoContainer?.let {
            val lpHeader = it.layoutParams as LinearLayout.LayoutParams
            lpHeader.height = headerSize
            lpHeader.width = headerSize
            it.requestLayout()
        }

        // setup photo
        val multimedia: Multimedia? = MultimediaConverter().fromString(timesEntity.multimedia)
        timesDraweeView?.setupImage(multimedia?.imageUrl, R.drawable.ic_insert_photo_white_24, object : TimesDraweeView.OnPhotoLoadListener {
            override fun onImageLoaded() {
                isPhotoLoaded = true
                timesLoader?.visibility = View.GONE
            }

            override fun onFailure() {
                isPhotoLoaded = false
                timesLoader?.visibility = View.GONE
            }
        })

        // setup info
        timesTitle?.text = timesEntity.title
        timesReviewer?.text = timesEntity.reviewer

        itemView?.setOnClickListener {
            onItemClickListener?.onItemClick(timesEntity, timesDraweeView)
        }
    }
}