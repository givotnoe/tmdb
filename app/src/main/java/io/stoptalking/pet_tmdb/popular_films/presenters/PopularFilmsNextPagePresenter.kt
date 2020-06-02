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

const val POPULAR_FILMS_NEXT_PAGE = "POPULAR_FILMS_NEXT_PAGE"

interface PopularFilmsNextPageView : NetworkMvpView {
    fun showNextPage(films : List<PopularFilmsDTO>)
}

class PopularFilmsNextPagePresenter(private val useCase: PopularFilmsUseCase,
                                    errorBehavior: ErrorBehavior<PopularFilmsNextPageView>,
                                    schedulerProvider : SchedulerProvider,
                                    subject: Subject<Event>) :
    EventMutualPresenterImpl<PopularFilmsNextPageView, Event, List<PopularFilmsDTO>>(
        POPULAR_FILMS_NEXT_PAGE,
        errorBehavior,
        schedulerProvider,
        subject
    ) {


    override val transformer: ObservableTransformer<Event, List<PopularFilmsDTO>> =
        ObservableTransformer { obs ->

            obs
                .flatMapMaybe { useCase.getNextPage() }
                .observeOn(schedulerProvider.getUI())
                .doOnNext { view?.showNextPage(it) }
        }

}

@Module
class PopularFilmsNextPagePresenterImpl {

    @Provides
    @ViewScope
    fun provideErrorBehavior () : ErrorBehavior<PopularFilmsNextPageView> =
        RetrofitErrorBehavior(SimpleBehavior())

    @Named(POPULAR_FILMS_NEXT_PAGE)
    @Provides
    @ViewScope
    fun provideSubject () : Subject<Event> =
        PublishSubject.create()

    @Provides
    @ViewScope
    @IntoMap
    @StringKey(POPULAR_FILMS_NEXT_PAGE)
    fun provide (useCase: PopularFilmsUseCase,
                 errorBehavior: ErrorBehavior<PopularFilmsNextPageView>,
                 schedulerProvider : SchedulerProvider,
                 @Named(POPULAR_FILMS_NEXT_PAGE) subject: Subject<Event>) : Presenter =
        PopularFilmsNextPagePresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )
}


