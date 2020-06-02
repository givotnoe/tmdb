package io.stoptalking.pet_tmdb.popular_films.presenters

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._presentation.presenters.base.*
import io.stoptalking.pet_tmdb._presentation.view.base.NetworkMvpView
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import java.util.concurrent.TimeUnit
import javax.inject.Named

const val POPULAR_FILMS_SHOW_DETAILS = "POPULAR_FILMS_SHOW_DETAILS"

interface PopularFilmsShowDetailsView : NetworkMvpView {
    fun showDetails(item : ShowDetailsDTO)
}

class PopularFilmsShowDetailsPresenter(errorBehavior: ErrorBehavior<PopularFilmsShowDetailsView>,
                                       schedulerProvider : SchedulerProvider,
                                       subject: Subject<ShowDetailsDTO>) :
    EventSimplePresenterImpl<PopularFilmsShowDetailsView, ShowDetailsDTO, ShowDetailsDTO>(
        POPULAR_FILMS_SHOW_DETAILS,
        errorBehavior,
        schedulerProvider,
        subject
    ) {

    override val transformer: ObservableTransformer<ShowDetailsDTO, ShowDetailsDTO> =
        ObservableTransformer { obs ->

            obs
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter { it.uuid != DEFAULT_INT }
                .observeOn(schedulerProvider.getUI())
                .doOnNext { view?.showDetails(it) }
        }
}

@Module
class PopularFilmsShowDetailsPresenterImpl {

    @Provides
    @ViewScope
    fun provideErrorBehavior () : ErrorBehavior<PopularFilmsShowDetailsView> =
        SimpleBehavior()

    @Named(POPULAR_FILMS_SHOW_DETAILS)
    @Provides
    @ViewScope
    fun provideSubject () : Subject<ShowDetailsDTO> =
        PublishSubject.create()

    @Provides
    @ViewScope
    @IntoMap
    @StringKey(POPULAR_FILMS_SHOW_DETAILS)
    fun provide (errorBehavior : ErrorBehavior<PopularFilmsShowDetailsView>,
                 schedulerProvider : SchedulerProvider,
                 @Named(POPULAR_FILMS_SHOW_DETAILS) subject: Subject<ShowDetailsDTO>) : Presenter =
        PopularFilmsShowDetailsPresenter(
            errorBehavior,
            schedulerProvider,
            subject
        )
}


