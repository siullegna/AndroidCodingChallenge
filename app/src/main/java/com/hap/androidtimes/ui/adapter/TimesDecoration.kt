package com.hap.androidtimes.ui.adapter

import android.content.res.Resources
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hap.androidtimes.R
import com.hap.androidtimes.TimesApplication
import javax.inject.Inject

/**
 * Created by luis on 5/19/18.
 */
class TimesDecoration : RecyclerView.ItemDecoration() {
    private var space: Int = 0
    @Inject
    protected lateinit var resources: Resources

    init {
        TimesApplication.getInstance().appComponent().inject(this)

        space = resources.getDimensionPixelSize(R.dimen.times_grid_item_margin)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.let {
            it.top = space
            it.left = space
            it.bottom = space
            it.right = space
        }
    }
}