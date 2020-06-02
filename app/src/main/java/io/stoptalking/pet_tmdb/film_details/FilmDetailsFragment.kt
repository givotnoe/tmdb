package io.stoptalking.pet_tmdb.film_details

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb.R
import io.stoptalking.pet_tmdb._data.image.ImageHandler
import io.stoptalking.pet_tmdb._presentation.view.base.BaseFragment
import io.stoptalking.pet_tmdb._presentation.view.base.BaseMvpFragment
import io.stoptalking.pet_tmdb.film_details.dagger.DaggerFilmDetailsComponent
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsDTO
import io.stoptalking.pet_tmdb.film_details.presenters.FILM_DETAILS_INIT
import io.stoptalking.pet_tmdb.film_details.presenters.FILM_DETAILS_RESTORE
import io.stoptalking.pet_tmdb.film_details.presenters.FilmDetailsView
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent
import kotlinx.android.synthetic.main.fragment_film_details.*
import javax.inject.Inject
import javax.inject.Named

class FilmDetailsFragment : BaseMvpFragment<BaseFragment.Listener>(),
    FilmDetailsView {

    companion object {

        const val TAG = "FilmDetailsFragment"

        private const val FILM_UUID_KEY = "FILM_UUID_KEY"

        fun newInstance (item : ShowDetailsDTO) : FilmDetailsFragment {

            val args = Bundle()
            args.putInt(FILM_UUID_KEY, item.uuid)

            val fragment =
                FilmDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    //abstract

    override fun getContentResId(): Int = R.layout.fragment_film_details

    override fun inject(component: ActivityComponent) {
        DaggerFilmDetailsComponent
            .builder()
            .activityComponent(component)
            .build()
            .inject(this)
    }

    //inject

    @field:Named("blue_span")
    @Inject
    lateinit var bSpan: ForegroundColorSpan

    @field:Named("blue_light_span")
    @Inject
    lateinit var blSpan: ForegroundColorSpan

    @Inject
    lateinit var imageHandler: ImageHandler

    @Inject
    @field:Named(FILM_DETAILS_INIT)
    lateinit var initSubject : Subject<Int>

    @Inject
    @field:Named(FILM_DETAILS_RESTORE)
    lateinit var restoreSubject : Subject<Int>


    //fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uuid = arguments?.getInt(FILM_UUID_KEY) ?: DEFAULT_INT

        if (savedInstanceState == null) {
            initSubject.onNext(uuid)
        } else {
            restoreSubject.onNext(uuid)
        }
    }

    //NetworkMvpView

    override fun onUnknownError(requestCode: String) {
        Toast
            .makeText(context, "Error occurred", Toast.LENGTH_LONG)
            .show()
    }

    override fun onNetworkServerError(requestCode: String, message: String?) {
        Toast
            .makeText(context, "Server error occurred", Toast.LENGTH_LONG)
            .show()
    }

    override fun onNetworkIOError(requestCode: String) {
        Toast
            .makeText(context, "Network error occurred", Toast.LENGTH_LONG)
            .show()
    }

    //FilmDetailsView

    override fun showDetails(dto: FilmDetailsDTO) {

        imageHandler.loadCenterCropImage(dto.image, film_details_image)

        film_details_rating_container.setRating(dto.rating)

        val str = getString(R.string.film_details_rating, dto.rating, dto.votesCount)
        val spannable = SpannableString(str)
        spannable.setSpan(blSpan, 0, str.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.setSpan(bSpan, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        film_details_rating.text = spannable

        film_details_about_content.text = dto.about
    }
}