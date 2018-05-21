package com.hap.androidtimes.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hap.androidtimes.R

/**
 * Created by luis on 5/19/18.
 */
class ErrorScreenView : LinearLayout {
    private val errorIcon: TimesDraweeView

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.error_screen_view, this)

        errorIcon = findViewById(R.id.error_icon)

        errorIcon.setupImage(errorIcon = R.drawable.ic_could_error)
    }
}