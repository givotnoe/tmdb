package io.stoptalking.pet_tmdb.popular_films.dagger

import dagger.Component
import io.stoptalking.pet_tmdb.popular_films.data.repositories.PopularFilmsRepositoryNetwork
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsInitPresenterImpl
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsNextPagePresenterImpl
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsRestorePresenterImpl
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsShowDetailsPresenterImpl
import io.stoptalking.pet_tmdb.popular_films.PopularFilmsFragment
import io.stoptalking.pet_tmdb.utils.dagger.ActivityComponent
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope

@ViewScope
@Component(dependencies = [ActivityComponent::class], modules = [

    PopularFilmsModule::class,

    PopularFilmsInitPresenterImpl::class,
    PopularFilmsRestorePresenterImpl::class,
    PopularFilmsNextPagePresenterImpl::class,
    PopularFilmsShowDetailsPresenterImpl::class,

    PopularFilmsRepositoryNetwork::class

])

interface PopularFilmsComponent {

    //injects
    fun inject (fragment : PopularFilmsFragment)
}