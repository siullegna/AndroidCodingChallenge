package com.hap.androidtimes.util

import android.app.Activity
import android.util.DisplayMetrics

/**
 * Created by luis on 5/16/18.
 */
object DeviceInfo {
    fun getScreenWidth(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}