package com.hap.androidtimes.ui.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.RotationOptions
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.hap.androidtimes.R

/**
 * Created by luis on 5/19/18.
 */
class TimesDraweeView : SimpleDraweeView {
    companion object {
        private const val FADE_DURATION = 300
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun setupImage(photoUrl: String? = null, errorIcon: Int, onPhotoLoadListener: OnPhotoLoadListener? = null) {
        val photoUri: Uri? = if (photoUrl.isNullOrEmpty()) {
            Uri.EMPTY
        } else {
            Uri.parse(photoUrl)
        }

        val failureDrawable = ContextCompat.getDrawable(context, errorIcon)
        failureDrawable?.let {
            DrawableCompat.setTint(it, ContextCompat.getColor(context, R.color.colorAccent))
        }

        val photoRequest = ImageRequestBuilder
                .newBuilderWithSource(photoUri)
                .setRotationOptions(RotationOptions.autoRotate())
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(photoRequest)
                .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                    override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                        onPhotoLoadListener?.onImageLoaded()
                    }

                    override fun onFailure(id: String?, throwable: Throwable?) {
                        onPhotoLoadListener?.onFailure()
                    }
                })
                .setOldController(this.controller)
                .build()

        val hierarchy = GenericDraweeHierarchyBuilder
                .newInstance(resources)
                .setFadeDuration(FADE_DURATION)
                .setFailureImage(failureDrawable)
                .build()

        this.hierarchy = hierarchy
        this.controller = controller
    }

    interface OnPhotoLoadListener {
        fun onImageLoaded()
        fun onFailure()
    }
}