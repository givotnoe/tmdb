package io.stoptalking.pet_tmdb._presentation.presenters.popular_films

import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb._presentation.presenters.base.RetrofitErrorBehavior
import io.stoptalking.pet_tmdb._presentation.presenters.base.SimpleBehavior
import io.stoptalking.pet_tmdb.httpException
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.domain.PopularFilmsUseCase
import io.stoptalking.pet_tmdb.popular_films.presenters.POPULAR_FILMS_NEXT_PAGE
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsNextPagePresenter
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsNextPageView
import io.stoptalking.pet_tmdb.schedulerProvider
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import java.io.IOException

class PopularFilmsNextPagePresenterTest {

    private val view = mock(PopularFilmsNextPageView::class.java)
    private val errorBehavior = RetrofitErrorBehavior<PopularFilmsNextPageView>(SimpleBehavior())
    private val useCase = mock(PopularFilmsUseCase::class.java)
    private val subject = PublishSubject.create<Event>()

    private val presenter =
        PopularFilmsNextPagePresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )

    @Test
    fun testEmptyList() {

        //given
        given(useCase.getNextPage())
            .willReturn(Maybe.empty())

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).shouldHaveNoInteractions()
    }

    @Test
    fun testNotEmptyList() {

        //given
        val items = listOf(PopularFilmsDTO.default())
        given(useCase.getNextPage())
            .willReturn(Maybe.just(items))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().showNextPage(items)
    }

    @Test
    fun testIoError() {

        //given
        given(useCase.getNextPage())
            .willReturn(Maybe.error(IOException()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onNetworkIOError(POPULAR_FILMS_NEXT_PAGE)
    }

    @Test
    fun testServerError() {

        //given
        given(useCase.getNextPage())
            .willReturn(Maybe.error(httpException))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onNetworkServerError(POPULAR_FILMS_NEXT_PAGE, httpException.message)
    }

    @Test
    fun testUnknownError() {

        //given
        given(useCase.getNextPage())
            .willReturn(Maybe.error(RuntimeException()))

        //when
        presenter.attach(view)
        subject.onNext(Event)

        //then
        then(view).should().onUnknownError(POPULAR_FILMS_NEXT_PAGE)
    }
}