package io.stoptalking.pet_tmdb._data.image

import android.view.View
import android.widget.ImageView

interface ImageHandler {

    fun loadSimpleImage (url: String, target: ImageView)
    fun loadCenterCropImage (url: String, target: ImageView)
    fun loadCircleCropImage (url: String, target: ImageView, defaultRes : Int = View.NO_ID)
    fun loadRoundCornerImage (url: String, radiusResId: Int, target: ImageView)
    fun loadCenterCropRoundCornerImage (url: String, radiusResId: Int, target: ImageView)
}