package com.hap.androidtimes.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hap.androidtimes.R
import com.hap.androidtimes.network.LoadingState
import com.hap.androidtimes.network.model.TimesItem
import com.hap.androidtimes.persistence.entity.TimesEntity
import com.hap.androidtimes.ui.adapter.TimesAdapter
import com.hap.androidtimes.ui.adapter.TimesDecoration
import com.hap.androidtimes.util.DeviceInfo
import com.hap.androidtimes.ui.widget.TimesDraweeView

class TimesActivity : BaseTimesActivity(), TimesAdapter.OnItemClickListener {
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvTimes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_times)
        super.onCreate(savedInstanceState)

        swipeRefresh = findViewById(R.id.swipe_refresh)
        rvTimes = findViewById(R.id.rv_times)

        showLoader()

        val screenWidth = DeviceInfo.getScreenWidth(this)
        val columns = resources.getInteger(R.integer.column_num)
        val marginDiff = resources.getDimensionPixelSize(R.dimen.times_grid_margin_diff)
        val headerSize = (screenWidth - marginDiff) / columns

        timesViewModel.timesAdapter.setHeaderSize(headerSize)
        timesViewModel.timesAdapter.setOnItemClickListener(this)

        rvTimes.addItemDecoration(TimesDecoration())
        rvTimes.setHasFixedSize(true)
        rvTimes.layoutManager = GridLayoutManager(this, columns)
        rvTimes.adapter = timesViewModel.timesAdapter

        timesViewModel.timesLiveData.observe(this, Observer {
            it?.let {
                when {
                    it.isNotEmpty() -> {
                        if (timesViewModel.loadingState == LoadingState.ERROR) {
                            showSnackBar(getString(R.string.error_snack_bar))
                        }
                        timesViewModel.timesAdapter.addAll(it)
                        showContent()
                    }
                    else -> {
                        if (timesViewModel.loadingState == LoadingState.ERROR) {
                            showError()
                        } else {
                            showEmptyScreen()
                        }
                    }
                }
            } ?: showError()
        })

        loadTimes()

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))

        swipeRefresh.setOnRefreshListener {
            when {
                timesViewModel.loadingState == LoadingState.ERROR -> {
                    hideError()
                    showLoader()
                }
                timesViewModel.timesAdapter.isEmpty() -> {
                    hideEmptyScreen()
                    showLoader()
                }
            }

            loadTimes()
        }
    }

    override fun showError() {
        super.showError()
        swipeRefresh.isRefreshing = false
    }

    override fun showEmptyScreen() {
        super.showEmptyScreen()
        swipeRefresh.isRefreshing = false
    }

    override fun showContent() {
        hideError()
        hideEmptyScreen()
        hideLoader()
        swipeRefresh.isRefreshing = false
        rvTimes.visibility = View.VISIBLE
    }

    override fun hideContent() {
        rvTimes.visibility = View.GONE
    }

    override fun onItemClick(timesEntity: TimesEntity, timesDraweeView: TimesDraweeView?) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra(DetailActivity.ARG_TIMES_ITEM_KEY, TimesItem.convert(timesEntity))
        startActivity(detailIntent)
    }

    private fun loadTimes() {
        if (timesViewModel.loadingState == LoadingState.LOADING) {
            return
        }

        timesViewModel.loadingState = LoadingState.LOADING

        timesViewModel.getTimesList()
                .observe(this, Observer {
                    when {
                        it != null -> timesViewModel.handleResponse(it)
                        timesViewModel.timesAdapter.isEmpty() -> {
                            showError()
                        }
                        else -> {
                            showSnackBar(getString(R.string.error_snack_bar))
                        }
                    }
                })
    }
}