package io.stoptalking.pet_tmdb._presentation.view.base.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._data.models.Event

class PaginationScrollBehavior(private val layoutManager: LinearLayoutManager,
                               private val subject : Subject<Event>,
                               private val threshold : Int) : RecyclerView.OnScrollListener() {

    companion object {
        const val THRESHOLD_3 = 3
        const val THRESHOLD_10 = 10
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition + visibleItemCount >= totalItemCount - threshold) {
            layoutManager.getChildAt(0)?.post { subject.onNext(Event) }
        }
    }
}