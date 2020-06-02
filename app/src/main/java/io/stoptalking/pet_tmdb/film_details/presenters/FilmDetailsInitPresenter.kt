package io.stoptalking.pet_tmdb.film_details.presenters

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._presentation.presenters.base.*
import io.stoptalking.pet_tmdb._presentation.view.base.NetworkMvpView
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsDTO
import io.stoptalking.pet_tmdb.film_details.domain.FilmDetailsUseCase
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Named

const val FILM_DETAILS_INIT = "FILM_DETAILS_INIT"

interface FilmDetailsView : NetworkMvpView {
    fun showDetails(dto: FilmDetailsDTO)
}

class FilmDetailsInitPresenter(private val useCase: FilmDetailsUseCase,
                               errorBehavior: ErrorBehavior<FilmDetailsView>,
                               schedulerProvider : SchedulerProvider,
                               subject: Subject<Int>) :
    EventMutualPresenterImpl<FilmDetailsView, Int, FilmDetailsDTO>(
        FILM_DETAILS_INIT,
        errorBehavior,
        schedulerProvider,
        subject
    ) {

    override val transformer: ObservableTransformer<Int, FilmDetailsDTO> =
        ObservableTransformer { obs ->

            obs
                .flatMapMaybe { uuid ->

                    useCase
                        .initDetails(uuid)
                        .observeOn(schedulerProvider.getUI())
                        .doOnSuccess { view?.showDetails(it) }
                }
        }
}

@Module
class FilmDetailsInitPresenterImpl {

    @Provides
    @ViewScope
    fun provideErrorBehavior () : ErrorBehavior<FilmDetailsView> =
        RetrofitErrorBehavior(SimpleBehavior())

    @Named(FILM_DETAILS_INIT)
    @Provides
    @ViewScope
    fun provideSubject () : Subject<Int> =
        PublishSubject.create()

    @Provides
    @ViewScope
    @IntoMap
    @StringKey(FILM_DETAILS_INIT)
    fun provide (useCase: FilmDetailsUseCase,
                 errorBehavior : ErrorBehavior<FilmDetailsView>,
                 schedulerProvider : SchedulerProvider,
                 @Named(FILM_DETAILS_INIT) subject: Subject<Int>) : Presenter =
        FilmDetailsInitPresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )
}