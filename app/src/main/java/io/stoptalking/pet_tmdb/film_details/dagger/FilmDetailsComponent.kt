package io.stoptalking.pet_tmdb.film_details.dagger

import dagger.Component
import io.stoptalking.pet_tmdb.film_details.data.repositories.FilmDetailsRepositoryNetwork
import io.stoptalking.pet_tmdb.film_details.presenters.FilmDetailsInitPresenterImpl
import io.stoptalking.pet_tmdb.film_details.presenters.FilmDetailsRestorePresenterImpl
import io.stoptalking.pet_tmdb.film_details.FilmDetailsFragment
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope

@ViewScope
@Component(dependencies = [ActivityComponent::class], modules = [

    FilmDetailsModule::class,

    FilmDetailsInitPresenterImpl::class,
    FilmDetailsRestorePresenterImpl::class,

    FilmDetailsRepositoryNetwork::class

])

interface FilmDetailsComponent {

    //injects
    fun inject (fragment : FilmDetailsFragment)
}