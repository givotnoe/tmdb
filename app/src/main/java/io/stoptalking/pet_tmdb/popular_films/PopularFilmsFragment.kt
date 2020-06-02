package io.stoptalking.pet_tmdb.popular_films

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb.R
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb._presentation.view.base.BaseFragment
import io.stoptalking.pet_tmdb._presentation.view.base.BaseMvpFragment
import io.stoptalking.pet_tmdb._presentation.view.base.adapter.PaginationScrollBehavior
import io.stoptalking.pet_tmdb.popular_films.adapter.PopularFilmsAdapter
import io.stoptalking.pet_tmdb.popular_films.dagger.DaggerPopularFilmsComponent
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import io.stoptalking.pet_tmdb.popular_films.presenters.*
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent
import kotlinx.android.synthetic.main.fragment_popular_films.*
import javax.inject.Inject
import javax.inject.Named

class PopularFilmsFragment : BaseMvpFragment<PopularFilmsFragment.Listener>(),
    PopularFilmsInitView,
    PopularFilmsRestoreView,
    PopularFilmsNextPageView,
    PopularFilmsShowDetailsView {

    companion object {

        const val TAG = "PopularFilmsFragment"

        fun newInstance () : PopularFilmsFragment {

            val args = Bundle()

            val fragment =
                PopularFilmsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    //abstract

    override fun getContentResId(): Int = R.layout.fragment_popular_films

    override fun inject(component: ActivityComponent) {
        DaggerPopularFilmsComponent
            .builder()
            .activityComponent(component)
            .build()
            .inject(this)
    }

    //inject

    @Inject
    lateinit var adapter : PopularFilmsAdapter

    @Inject
    lateinit var layoutManager : LinearLayoutManager

    @Inject
    lateinit var scrollBehavior : PaginationScrollBehavior

    @Inject
    @field:Named(POPULAR_FILMS_INIT)
    lateinit var initSubject : Subject<Event>

    @Inject
    @field:Named(POPULAR_FILMS_RESTORE)
    lateinit var restoreSubject : Subject<Event>


    //fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popular_films_rv.layoutManager = layoutManager
        popular_films_rv.adapter = adapter

        //presenter
        if (savedInstanceState == null) {
            initSubject.onNext(Event)
        } else {
            restoreSubject.onNext(Event)
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

    //PopularFilmsInitPresenter

    override fun initFilms(films: List<PopularFilmsDTO>) {
        adapter.replaceItems(films)
        popular_films_rv.addOnScrollListener(scrollBehavior)
    }

    //PopularFilmsRestorePresenter

    override fun restoreFilms(films: List<PopularFilmsDTO>) {
        initFilms(films)
    }

    //PopularFilmsNextPagePresenter

    override fun showNextPage(films: List<PopularFilmsDTO>) {
        adapter.addItems(films)
    }

    //PopularFilmsShowDetailsView

    override fun showDetails(item : ShowDetailsDTO) {
        listener?.showFilmDetails(item)
    }

    //listener

    interface Listener : BaseFragment.Listener {
        fun showFilmDetails(item : ShowDetailsDTO)
    }
}