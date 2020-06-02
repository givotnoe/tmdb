package io.stoptalking.pet_tmdb.popular_films.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb.R
import io.stoptalking.pet_tmdb._data.image.ImageHandler
import io.stoptalking.pet_tmdb._presentation.view.base.adapter.BaseAdapter
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import io.stoptalking.pet_tmdb.popular_films.presenters.POPULAR_FILMS_SHOW_DETAILS
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Inject
import javax.inject.Named

@ViewScope
class PopularFilmsAdapter @Inject constructor(
    context: Context,
    private val imageHandler: ImageHandler,
    @Named(POPULAR_FILMS_SHOW_DETAILS) private val subject: Subject<ShowDetailsDTO>
) : BaseAdapter<PopularFilmsDTO, PopularFilmsHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilmsHolder =
        PopularFilmsHolder(inflater.inflate(R.layout.item_popular_films, parent, false), imageHandler, subject)

    override fun onBindViewHolder(holder: PopularFilmsHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        subject.onComplete()
    }
}