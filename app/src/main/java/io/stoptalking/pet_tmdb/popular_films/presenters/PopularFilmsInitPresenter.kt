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

const val POPULAR_FILMS_INIT = "POPULAR_FILMS_INIT"

interface PopularFilmsInitView : NetworkMvpView {
    fun initFilms(films : List<PopularFilmsDTO>)
}

class PopularFilmsInitPresenter(private val useCase: PopularFilmsUseCase,
                                errorBehavior: ErrorBehavior<PopularFilmsInitView>,
                                schedulerProvider : SchedulerProvider,
                                subject: Subject<Event>) :
    EventMutualPresenterImpl<PopularFilmsInitView, Event, List<PopularFilmsDTO>>(
        POPULAR_FILMS_INIT,
        errorBehavior,
        schedulerProvider,
        subject
    ) {

    override val transformer: ObservableTransformer<Event, List<PopularFilmsDTO>> =
        ObservableTransformer { obs ->

            obs
                .flatMapSingle {

                    useCase
                        .initFilms()
                        .observeOn(schedulerProvider.getUI())
                        .doOnSuccess { view?.initFilms(it) }
                }
        }
}

@Module
class PopularFilmsInitPresenterImpl {

    @Provides
    @ViewScope
    fun provideErrorBehavior () : ErrorBehavior<PopularFilmsInitView> =
        RetrofitErrorBehavior(SimpleBehavior())

    @Named(POPULAR_FILMS_INIT)
    @Provides
    @ViewScope
    fun provideSubject () : Subject<Event> =
        PublishSubject.create()

    @Provides
    @ViewScope
    @IntoMap
    @StringKey(POPULAR_FILMS_INIT)
    fun provide (useCase: PopularFilmsUseCase,
                 errorBehavior: ErrorBehavior<PopularFilmsInitView>,
                 schedulerProvider : SchedulerProvider,
                 @Named(POPULAR_FILMS_INIT) subject: Subject<Event>) : Presenter =
        PopularFilmsInitPresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )
}