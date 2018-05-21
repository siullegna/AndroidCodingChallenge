package com.hap.androidtimes.ui

/**
 * Created by luis on 5/16/18.
 */
interface AppInterface {
    fun showLoader()
    fun hideLoader()
    fun showEmptyScreen()
    fun hideEmptyScreen()
    fun showContent()
    fun hideContent()
    fun showError()
    fun hideError()
    fun showSnackBar(message: String)
}