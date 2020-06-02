package io.stoptalking.pet_tmdb.main

import android.os.Bundle
import io.stoptalking.pet_tmdb.R
import io.stoptalking.pet_tmdb._presentation.view.base.BaseActivity
import io.stoptalking.pet_tmdb._presentation.view.base.BaseFragment
import io.stoptalking.pet_tmdb.film_details.FilmDetailsFragment
import io.stoptalking.pet_tmdb.main.dagger.DaggerMainComponent
import io.stoptalking.pet_tmdb.popular_films.PopularFilmsFragment
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent

class MainActivity : BaseActivity(),
    BaseFragment.Listener,
    PopularFilmsFragment.Listener {

    //abstract

    override fun getContentResId(): Int = R.layout.activity_main

    override fun inject(activityComponent: ActivityComponent) {
        DaggerMainComponent
            .builder()
            .activityComponent(activityComponent)
            .build()
            .inject(this)
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        if (state == null) {

            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_content, PopularFilmsFragment.newInstance(), PopularFilmsFragment.TAG)
                .commit()
        }
    }

    //PopularFilmsFragment.Listener

    override fun showFilmDetails(item : ShowDetailsDTO) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_content, FilmDetailsFragment.newInstance(item), FilmDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }
}
