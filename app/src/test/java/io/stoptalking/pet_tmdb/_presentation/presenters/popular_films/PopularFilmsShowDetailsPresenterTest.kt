package io.stoptalking.pet_tmdb._presentation.presenters.popular_films

import io.reactivex.subjects.PublishSubject
import io.stoptalking.pet_tmdb._presentation.presenters.base.SimpleBehavior
import io.stoptalking.pet_tmdb.popular_films.data.ShowDetailsDTO
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsShowDetailsPresenter
import io.stoptalking.pet_tmdb.popular_films.presenters.PopularFilmsShowDetailsView
import io.stoptalking.pet_tmdb.schedulerProvider
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock

class PopularFilmsShowDetailsPresenterTest {

    private val view = mock(PopularFilmsShowDetailsView::class.java)
    private val errorBehavior = SimpleBehavior<PopularFilmsShowDetailsView>()
    private val subject = PublishSubject.create<ShowDetailsDTO>()
    private val dto = mock(ShowDetailsDTO::class.java)

    private val presenter =
        PopularFilmsShowDetailsPresenter(
            errorBehavior,
            schedulerProvider,
            subject
        )

    @Test
    fun testCorrectDTO() {

        //given
        given(dto.uuid).willReturn(3)

        //when
        presenter.attach(view)
        subject.onNext(dto)

        //then
        then(view).should().showDetails(dto)
    }

    @Test
    fun testWrongDTO() {

        //given
        given(dto.uuid).willReturn(DEFAULT_INT)

        //when
        presenter.attach(view)
        subject.onNext(dto)

        //then
        then(view).shouldHaveNoInteractions()
    }
}