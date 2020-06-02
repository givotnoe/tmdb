package io.stoptalking.pet_tmdb._presentation.presenters.film_details

import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject
import io.stoptalking.pet_tmdb._presentation.presenters.base.RetrofitErrorBehavior
import io.stoptalking.pet_tmdb._presentation.presenters.base.SimpleBehavior
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsDTO
import io.stoptalking.pet_tmdb.film_details.domain.FilmDetailsUseCase
import io.stoptalking.pet_tmdb.film_details.presenters.FILM_DETAILS_INIT
import io.stoptalking.pet_tmdb.film_details.presenters.FilmDetailsInitPresenter
import io.stoptalking.pet_tmdb.film_details.presenters.FilmDetailsView
import io.stoptalking.pet_tmdb.httpException
import io.stoptalking.pet_tmdb.schedulerProvider
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import java.io.IOException

class FilmDetailsInitPresenterTest {

    private val view = mock(FilmDetailsView::class.java)
    private val errorBehavior = RetrofitErrorBehavior<FilmDetailsView>(SimpleBehavior())
    private val useCase = mock(FilmDetailsUseCase::class.java)
    private val dto = mock(FilmDetailsDTO::class.java)
    private val subject = PublishSubject.create<Int>()

    private val presenter =
        FilmDetailsInitPresenter(
            useCase,
            errorBehavior,
            schedulerProvider,
            subject
        )

    @Test
    fun testCorrectUuid() {

        //given
        given(dto.uuid)
            .willReturn(3)

        given(useCase.initDetails(3))
            .willReturn(Maybe.just(dto))

        //when
        presenter.attach(view)
        subject.onNext(dto.uuid)

        //then
        then(view).should().showDetails(dto)
    }

    @Test
    fun testDefaultUuid() {

        //given
        given(dto.uuid)
            .willReturn(DEFAULT_INT)

        given(useCase.initDetails(DEFAULT_INT))
            .willReturn(Maybe.empty())

        //when
        presenter.attach(view)
        subject.onNext(dto.uuid)

        //then
        then(view).shouldHaveNoMoreInteractions()
    }

    @Test
    fun testIoError() {

        //given
        given(dto.uuid)
            .willReturn(3)

        given(useCase.initDetails(3))
            .willReturn(Maybe.error(IOException()))

        //when
        presenter.attach(view)
        subject.onNext(dto.uuid)

        //then
        then(view).should().onNetworkIOError(FILM_DETAILS_INIT)
    }

    @Test
    fun testServerError() {

        //given
        given(dto.uuid)
            .willReturn(3)

        given(useCase.initDetails(3))
            .willReturn(Maybe.error(httpException))

        //when
        presenter.attach(view)
        subject.onNext(dto.uuid)

        //then
        then(view).should().onNetworkServerError(FILM_DETAILS_INIT, httpException.message)
    }

    @Test
    fun testUnknownError() {

        //given
        given(dto.uuid)
            .willReturn(3)

        given(useCase.initDetails(3))
            .willReturn(Maybe.error(RuntimeException()))

        //when
        presenter.attach(view)
        subject.onNext(dto.uuid)

        //then
        then(view).should().onUnknownError(FILM_DETAILS_INIT)
    }
}