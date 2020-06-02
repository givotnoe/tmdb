package io.stoptalking.pet_tmdb.popular_films.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb.R
import io.stoptalking.pet_tmdb._data.image.ImageHandler
import io.stoptalking.pet_tmdb._presentation.view.base.custom.RatingView
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import kotlinx.android.synthetic.main.item_popular_films.view.*

class PopularFilmsHolder (view: View,
                          private val imageHandler: ImageHandler,
                          subject: Subject<ShowDetailsDTO>) : RecyclerView.ViewHolder(view) {

    private val image : ImageView = view.item_popular_films_image
    private val title : TextView = view.item_popular_films_title

    private val ratingView : RatingView = view.item_popular_films_rating_container as RatingView
    private val rating : TextView = view.item_popular_films_rating

    private val date : TextView = view.item_popular_films_date

    private var dto : PopularFilmsDTO = PopularFilmsDTO.default()

    init {
        RxView
            .clicks(view)
            .map { ShowDetailsDTO.from(dto) }
            .subscribe(subject)
    }

    fun bind (dto : PopularFilmsDTO) {

        this.dto = dto

        imageHandler.loadCenterCropRoundCornerImage(dto.image, R.dimen.item_popular_films_image_radius, image)
        title.text = dto.title

        ratingView.setRating(dto.rating)
        rating.text = dto.rating.toString()

        date.text = dto.date
    }

}