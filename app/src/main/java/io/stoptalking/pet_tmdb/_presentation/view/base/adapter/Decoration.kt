package io.stoptalking.pet_tmdb._presentation.view.base.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT

class HorizontalDividerDecoration(private val divider: Drawable?) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {

            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider?.intrinsicHeight ?: DEFAULT_INT)

            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?: DEFAULT_INT - 1) {
            outRect.bottom = divider?.intrinsicHeight ?: DEFAULT_INT
        }

    }
}

class VerticalDividerDecoration(private val divider: Drawable?, private val paddingBottom : Int) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom - paddingBottom

        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {

            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = child.right + params.rightMargin
            val right = left + (divider?.intrinsicWidth ?: DEFAULT_INT)

            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount ?: DEFAULT_INT - 1) {
            outRect.right = divider?.intrinsicWidth ?: DEFAULT_INT
            outRect.bottom = paddingBottom
        }
    }
}