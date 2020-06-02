package io.stoptalking.pet_tmdb._presentation.view.base.custom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import io.stoptalking.pet_tmdb.R

class RatingView : LinearLayout {

    private var maxRating = 0.0
    private var chCount = 5
    private var chSize = 10
    private var chMargin = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) { init(attrs) }
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) { init(attrs) }

    private fun init (attrs : AttributeSet) {

        context
            .theme
            .obtainStyledAttributes(
                attrs,
                R.styleable.RatingView,
                0,
                0
            )
            .apply {

                try {

                    val rating = getFloat(R.styleable.RatingView_maxRating, 0.0f)
                    maxRating = if (rating < 1) 1.0 else rating.toDouble()

                    chCount = getInt(R.styleable.RatingView_childCount, 5)

                    chSize = getDimensionPixelOffset(R.styleable.RatingView_childSize, 10)
                    chMargin = getDimensionPixelOffset(R.styleable.RatingView_childMargin, 0)

                } finally {
                    recycle()
                }
            }

        orientation = HORIZONTAL

        for (i in 0 until chCount) {

            val child = AppCompatImageView(context)

            val params = LayoutParams (chSize, chSize)
            params.gravity = Gravity.CENTER_VERTICAL

            if (i != 0) {
                params.marginStart = chMargin
            }

            child.layoutParams = params
            addView(child)
        }
    }

    fun setRating(rating : Double) {

        val threshold = (rating * childCount / maxRating).toInt()

        for (i in 0 until childCount) {

            val res =
                if (i < threshold)
                    R.drawable.rating_icon_active
                else
                    R.drawable.rating_icon_inactive

            (getChildAt(i) as? ImageView)?.setImageResource(res)

        }
    }
}