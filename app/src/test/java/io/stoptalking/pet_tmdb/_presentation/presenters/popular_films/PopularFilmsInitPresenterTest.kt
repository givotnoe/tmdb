package io.stoptalking.pet_tmdb._presentation.presenters.popular_films

import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb._presentation.presenters.base.RetrofitErrorBehavior
import io.stoptalking.pet_tmdb._presentation.presenters.base.SimpleBehavior
import io.stoptalking.pet_tmdb.httpException
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.domain.PopularFilmsUseCase
import io.stoptalking.pet_tmdb.popular_films.presenters.POPULAR_FILMS_INIT
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsInitPresenter
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsInitView
import io.stoptalking.pet_tmdb.schedulerProvider
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import java.io.IOException

class PopularFilmsInitPresenterTest {

    private val view = mock(PopularFilmsInitView::class.java)
    private val errorBehavior = RetrofitErrorBehavior<PopularFilmsInitView>(SimpleBehavior())
    private val useCase = mock(PopularFilmsUseCase::class.java)
    private val subject = PublishSubject.create<Event>()

    private val presenter =
        PopularFilmsInitPresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )

    @Test
    fun testEmptyList() {

        //given
        given(useCase.initFilms())
            .willReturn(Single.just(emptyList()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().initFilms(emptyList())
    }

    @Test
    fun testNotEmptyList() {

        //given
        val items = listOf(PopularFilmsDTO.default())
        given(useCase.initFilms())
            .willReturn(Single.just(items))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().initFilms(items)
    }

    @Test
    fun testIoError() {

        //given
        given(useCase.initFilms())
            .willReturn(Single.error(IOException()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onNetworkIOError(POPULAR_FILMS_INIT)
    }

    @Test
    fun testServerError() {

        //given
        given(useCase.initFilms())
            .willReturn(Single.error(httpException))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onNetworkServerError(POPULAR_FILMS_INIT, httpException.message)
    }

    @Test
    fun testUnknownError() {

        //given
        given(useCase.initFilms())
            .willReturn(Single.error(RuntimeException()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onUnknownError(POPULAR_FILMS_INIT)
    }
}