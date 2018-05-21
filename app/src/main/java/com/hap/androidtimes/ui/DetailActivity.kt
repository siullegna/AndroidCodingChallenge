package com.hap.androidtimes.ui

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.AppCompatTextView
import android.view.MenuItem
import android.view.View
import com.hap.androidtimes.R
import com.hap.androidtimes.network.model.TimesItem
import com.hap.androidtimes.ui.widget.TimesDraweeView
import kotlinx.android.synthetic.main.notification_template_part_time.*

class DetailActivity : BaseTimesActivity() {
    companion object {
        const val ARG_TIMES_ITEM_KEY = "com.hap.androidtimes.ui.ARG_TIMES_ITEM_KEY"
    }

    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private lateinit var timesDraweeView: TimesDraweeView
    private lateinit var content: View
    private lateinit var headline: AppCompatTextView
    private lateinit var summaryShort: AppCompatTextView
    private lateinit var byline: AppCompatTextView
    private lateinit var mpaaRating: AppCompatTextView
    private lateinit var publicationDate: AppCompatTextView

    private var timesItem: TimesItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
        super.onCreate(savedInstanceState)

        collapsingToolbar = findViewById(R.id.collapsing_toolbar)
        timesDraweeView = findViewById(R.id.times_drawee_view)
        content = findViewById(R.id.content)
        headline = findViewById(R.id.headline)
        summaryShort = findViewById(R.id.summary_short)
        byline = findViewById(R.id.byline)
        mpaaRating = findViewById(R.id.mpaa_rating)
        publicationDate = findViewById(R.id.publication_date)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        timesItem = intent?.getParcelableExtra(ARG_TIMES_ITEM_KEY)

        showLoader()

        timesItem?.let {
            collapsingToolbar.title = timesItem?.title
            timesDraweeView.setupImage(timesItem?.multimedia?.imageUrl, R.drawable.ic_could_error)
            headline.text = timesItem?.headline
            summaryShort.text = timesItem?.summary
            byline.text = getString(R.string.details_reviewer, timesItem?.reviewer)
            mpaaRating.text = getString(R.string.details_rating, timesItem?.rating)
            publicationDate.text = getString(R.string.details_publication_date, timesItem?.publicationDate)
            showContent()
        } ?: showError()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item?.let {
            when (it.itemId) {
                android.R.id.home -> {
                    supportFinishAfterTransition()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } ?: super.onOptionsItemSelected(item)
    }

    override fun showContent() {
        hideError()
        hideEmptyScreen()
        hideLoader()
        content.visibility = View.VISIBLE
    }

    override fun hideContent() {
        content.visibility = View.GONE
    }
}