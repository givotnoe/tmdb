package io.stoptalking.pet_tmdb._presentation.presenters.popular_films

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb._presentation.presenters.base.RetrofitErrorBehavior
import io.stoptalking.pet_tmdb._presentation.presenters.base.SimpleBehavior
import io.stoptalking.pet_tmdb.httpException
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.domain.PopularFilmsUseCase
import io.stoptalking.pet_tmdb.popular_films.presenters.POPULAR_FILMS_RESTORE
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsRestorePresenter
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsRestoreView
import io.stoptalking.pet_tmdb.schedulerProvider
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import java.io.IOException

class PopularFilmsRestorePresenterTest {

    private val view = mock(PopularFilmsRestoreView::class.java)
    private val errorBehavior = RetrofitErrorBehavior<PopularFilmsRestoreView>(SimpleBehavior())
    private val useCase = mock(PopularFilmsUseCase::class.java)
    private val subject = PublishSubject.create<Event>()

    private val presenter =
        PopularFilmsRestorePresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )

    @Test
    fun testEmptyList() {

        //given
        given(useCase.restoreFilms())
            .willReturn(Single.just(emptyList()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().restoreFilms(emptyList())
    }

    @Test
    fun testNotEmptyList() {

        //given
        val items = listOf(PopularFilmsDTO.default())
        given(useCase.restoreFilms())
            .willReturn(Single.just(items))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().restoreFilms(items)
    }

    @Test
    fun testIoError() {

        //given
        given(useCase.restoreFilms())
            .willReturn(Single.error(IOException()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onNetworkIOError(POPULAR_FILMS_RESTORE)
    }

    @Test
    fun testServerError() {

        //given
        given(useCase.restoreFilms())
            .willReturn(Single.error(httpException))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onNetworkServerError(POPULAR_FILMS_RESTORE, httpException.message)
    }

    @Test
    fun testUnknownError() {

        //given
        given(useCase.restoreFilms())
            .willReturn(Single.error(RuntimeException()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onUnknownError(POPULAR_FILMS_RESTORE)
    }
}