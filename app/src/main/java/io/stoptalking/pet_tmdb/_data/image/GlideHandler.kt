package io.stoptalking.pet_tmdb._data.image

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import io.stoptalking.pet_tmdb.utils.DEFAULT_STRING


class GlideHandler (private val context : Context,
                    private val baseUrl : String) : ImageHandler {

    private val manager = Glide.with(context)

    override fun loadSimpleImage(url: String, target: ImageView) {

        if (url == DEFAULT_STRING) {

            //clear image
            manager.clear(target)
            target.setImageDrawable(null)

        } else {

            //loading
            val rUrl = if (hasScheme(url)) {
                url
            } else {
                baseUrl + url
            }

            manager
                .load(rUrl)
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder(150).setCrossFadeEnabled(true)))
                .into(target)
        }
    }

    override fun loadCenterCropImage(url: String, target: ImageView) {

        if (url == DEFAULT_STRING) {

            //clear image
            manager.clear(target)
            target.setImageDrawable(null)

        } else {

            //req options
            val reqOptions = RequestOptions()
                .transform(CenterCrop())

            //loading
            val rUrl = if (hasScheme(url)) {
                url
            } else {
                baseUrl + url
            }

            manager
                .load(rUrl)
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder(150).setCrossFadeEnabled(true)))
                .apply(reqOptions)
                .into(target)
        }
    }

    override fun loadCircleCropImage(url: String, target: ImageView, defaultRes : Int) {

        if (url == DEFAULT_STRING) {

            //clear image
            manager.clear(target)

            if (defaultRes != View.NO_ID) {
                target.setImageResource(defaultRes)
            } else {
                target.setImageDrawable(null)
            }

        } else {

            //req options
            var reqOptions = RequestOptions()
                .transform(CircleCrop())

            if (defaultRes != View.NO_ID) {
                reqOptions = reqOptions.placeholder(defaultRes)
            }

            //loading
            val rUrl = if (hasScheme(url)) {
                url
            } else {
                baseUrl + url
            }

            manager
                .load(rUrl)
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder(150).setCrossFadeEnabled(true)))
                .apply(reqOptions)
                .into(target)
        }
    }

    override fun loadRoundCornerImage(url: String, radiusResId: Int, target: ImageView) {

        val radius = context.resources.getDimensionPixelOffset(radiusResId)

        if (url == DEFAULT_STRING) {

            //clear image
            manager.clear(target)
            target.setImageDrawable(null)

        } else {

            //req options
            val reqOptions = RequestOptions()
                .transform(RoundedCorners(radius))

            //loading
            val rUrl = if (hasScheme(url)) {
                url
            } else {
                baseUrl + url
            }

            manager
                .load(rUrl)
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder(150).setCrossFadeEnabled(true)))
                .apply(reqOptions)
                .into(target)
        }
    }

    override fun loadCenterCropRoundCornerImage(url: String, radiusResId: Int, target: ImageView) {

        val radius = context.resources.getDimensionPixelOffset(radiusResId)

        if (url == DEFAULT_STRING) {

            //clear image
            manager.clear(target)
            target.setImageDrawable(null)

        } else {

            //req options
            val reqOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(radius))

            //loading
            val rUrl = if (hasScheme(url)) {
                url
            } else {
                baseUrl + url
            }

            manager
                .load(rUrl)
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder(150).setCrossFadeEnabled(true)))
                .apply(reqOptions)
                .into(target)
        }
    }

    //common

    private fun hasScheme (url : String) : Boolean = Uri.parse(url).scheme != null
}