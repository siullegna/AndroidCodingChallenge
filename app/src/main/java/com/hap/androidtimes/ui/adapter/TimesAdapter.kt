package com.hap.androidtimes.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hap.androidtimes.R
import com.hap.androidtimes.persistence.entity.TimesEntity
import com.hap.androidtimes.ui.holder.TimesViewHolder
import com.hap.androidtimes.ui.widget.TimesDraweeView

/**
 * Created by luis on 5/19/18.
 */
class TimesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val timesList: ArrayList<TimesEntity> = ArrayList()
    private var headerSize: Int = -1
    private var onItemClickListener: OnItemClickListener? = null

    fun setHeaderSize(headerSize: Int) {
        this.headerSize = headerSize
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun isEmpty(): Boolean = timesList.isEmpty()

    private fun getTimesByPosition(position: Int): TimesEntity? {
        return if (timesList.isNotEmpty()) {
            timesList[position]
        } else {
            null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context).inflate(R.layout.times_view_holder, parent, false)
        return TimesViewHolder(itemView)
    }

    override fun getItemCount(): Int = timesList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (headerSize <= 0) {
            throw IllegalArgumentException("You must provide a {headerSize} > 0")
        }

        val timesEntity = getTimesByPosition(position)
        timesEntity?.let {
            (holder as TimesViewHolder).setupView(it, headerSize, onItemClickListener)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(timesEntity: TimesEntity, timesDraweeView: TimesDraweeView?)
    }

    fun addAll(timesList: List<TimesEntity>) {
        this.timesList.clear()
        this.timesList.addAll(timesList)
        notifyDataSetChanged()
    }
}