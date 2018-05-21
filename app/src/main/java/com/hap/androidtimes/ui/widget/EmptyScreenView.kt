package com.hap.androidtimes.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hap.androidtimes.R

/**
 * Created by luis on 5/19/18.
 */
class EmptyScreenView : LinearLayout {
    private val emptyIcon: TimesDraweeView

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.empty_screen_view, this)

        emptyIcon = findViewById(R.id.empty_icon)

        emptyIcon.setupImage(errorIcon = R.drawable.ic_empty_list)
    }
}