package io.stoptalking.pet_tmdb.popular_films.presenters

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb._presentation.presenters.base.*
import io.stoptalking.pet_tmdb._presentation.view.base.NetworkMvpView
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.domain.PopularFilmsUseCase
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Named

const val POPULAR_FILMS_RESTORE = "POPULAR_FILMS_RESTORE"

interface PopularFilmsRestoreView : NetworkMvpView {
    fun restoreFilms(films : List<PopularFilmsDTO>)
}

class PopularFilmsRestorePresenter(private val useCase: PopularFilmsUseCase,
                                   errorBehavior: ErrorBehavior<PopularFilmsRestoreView>,
                                   schedulerProvider : SchedulerProvider,
                                   subject: Subject<Event>):
    EventMutualPresenterImpl<PopularFilmsRestoreView, Event, List<PopularFilmsDTO>>(
        POPULAR_FILMS_RESTORE,
        errorBehavior,
        schedulerProvider,
        subject) {

    override val transformer: ObservableTransformer<Event, List<PopularFilmsDTO>> =
        ObservableTransformer { obs ->

            obs
                .flatMapSingle {

                    useCase
                        .restoreFilms()
                        .observeOn(schedulerProvider.getUI())
                        .doOnSuccess { view?.restoreFilms(it) }
                }
        }
}

@Module
class PopularFilmsRestorePresenterImpl {

    @Provides
    @ViewScope
    fun provideErrorBehavior () : ErrorBehavior<PopularFilmsRestoreView> =
        RetrofitErrorBehavior(SimpleBehavior())

    @Named(POPULAR_FILMS_RESTORE)
    @Provides
    @ViewScope
    fun provideSubject () : Subject<Event> =
        PublishSubject.create()

    @Provides
    @ViewScope
    @IntoMap
    @StringKey(POPULAR_FILMS_RESTORE)
    fun provide (useCase: PopularFilmsUseCase,
                 errorBehavior: ErrorBehavior<PopularFilmsRestoreView>,
                 schedulerProvider : SchedulerProvider,
                 @Named(POPULAR_FILMS_RESTORE) subject: Subject<Event>) : Presenter =
        PopularFilmsRestorePresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )
}