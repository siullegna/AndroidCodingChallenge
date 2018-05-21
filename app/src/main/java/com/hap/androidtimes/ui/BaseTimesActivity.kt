package com.hap.androidtimes.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import com.hap.androidtimes.R
import com.hap.androidtimes.TimesApplication
import com.hap.androidtimes.persistence.viewmodel.TimesViewModel
import com.hap.androidtimes.persistence.viewmodel.ViewModelFactory
import com.hap.androidtimes.ui.widget.EmptyScreenView
import com.hap.androidtimes.ui.widget.ErrorScreenView
import javax.inject.Inject

/**
 * Created by luis on 5/16/18.
 */
abstract class BaseTimesActivity : AppCompatActivity(), AppInterface {
    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory
    protected lateinit var timesViewModel: TimesViewModel

    private var snackBar: Snackbar? = null
    private lateinit var root: CoordinatorLayout
    private lateinit var toolbar: Toolbar
    private lateinit var loader: ProgressBar
    private lateinit var emptyScreenView: EmptyScreenView
    private lateinit var errorScreenView: ErrorScreenView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TimesApplication.getInstance().appComponent().inject(this)

        timesViewModel = ViewModelProviders.of(this, viewModelFactory).get(TimesViewModel::class.java)

        root = findViewById(R.id.root)
        toolbar = findViewById(R.id.toolbar)
        loader = findViewById(R.id.loader)
        emptyScreenView = findViewById(R.id.empty_screen_view)
        errorScreenView = findViewById(R.id.error_screen_view)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun showLoader() {
        loader.visibility = View.VISIBLE
        hideEmptyScreen()
        hideError()
        hideContent()
    }

    override fun hideLoader() {
        loader.visibility = View.GONE
    }

    override fun showEmptyScreen() {
        emptyScreenView.visibility = View.VISIBLE
        hideLoader()
        hideError()
        hideContent()
    }

    override fun hideEmptyScreen() {
        emptyScreenView.visibility = View.GONE
    }

    override fun showError() {
        errorScreenView.visibility = View.VISIBLE
        hideLoader()
        hideEmptyScreen()
        hideContent()
    }

    override fun hideError() {
        errorScreenView.visibility = View.GONE
    }

    override fun showSnackBar(message: String) {
        snackBar?.dismiss()

        snackBar = Snackbar.make(root, message, Snackbar.LENGTH_LONG)

        snackBar?.show()
    }
}