package io.stoptalking.pet_tmdb.film_details.presenters

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._presentation.presenters.base.ErrorBehavior
import io.stoptalking.pet_tmdb._presentation.presenters.base.EventMutualPresenterImpl
import io.stoptalking.pet_tmdb._presentation.presenters.base.Presenter
import io.stoptalking.pet_tmdb._presentation.presenters.base.SchedulerProvider
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsDTO
import io.stoptalking.pet_tmdb.film_details.domain.FilmDetailsUseCase
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Named

const val FILM_DETAILS_RESTORE = "FILM_DETAILS_RESTORE"

class FilmDetailsRestorePresenter(private val useCase: FilmDetailsUseCase,
                                  errorBehavior: ErrorBehavior<FilmDetailsView>,
                                  schedulerProvider : SchedulerProvider,
                                  subject: Subject<Int>) :
    EventMutualPresenterImpl<FilmDetailsView, Int, FilmDetailsDTO>(
        FILM_DETAILS_RESTORE,
        errorBehavior,
        schedulerProvider,
        subject
    ) {

    override val transformer: ObservableTransformer<Int, FilmDetailsDTO> =
        ObservableTransformer { obs ->

            obs
                .flatMapMaybe { uuid ->

                    useCase
                        .restoreDetails(uuid)
                        .observeOn(schedulerProvider.getUI())
                        .doOnSuccess { view?.showDetails(it) }
                }
        }
}

@Module
class FilmDetailsRestorePresenterImpl {

    @Named(FILM_DETAILS_RESTORE)
    @Provides
    @ViewScope
    fun provideSubject () : Subject<Int> =
        PublishSubject.create()

    @Provides
    @ViewScope
    @IntoMap
    @StringKey(FILM_DETAILS_RESTORE)
    fun provide (useCase: FilmDetailsUseCase,
                 errorBehavior : ErrorBehavior<FilmDetailsView>,
                 schedulerProvider : SchedulerProvider,
                 @Named(FILM_DETAILS_RESTORE) subject: Subject<Int>) : Presenter =
        FilmDetailsInitPresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )
}