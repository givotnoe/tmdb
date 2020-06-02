package io.stoptalking.pet_tmdb.popular_films.domain

import io.reactivex.Maybe
import io.reactivex.Single
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.data.repositories.PopularFilmsRepository
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Inject

@ViewScope
class PopularFilmsUseCase @Inject constructor(private val popularFilmsRepository : PopularFilmsRepository) {

    fun initFilms() : Single<List<PopularFilmsDTO>> =
        popularFilmsRepository
            .refreshFilms()

    fun restoreFilms() : Single<List<PopularFilmsDTO>> =
        popularFilmsRepository
            .getFilms()

    fun getNextPage() : Maybe<List<PopularFilmsDTO>> =
        popularFilmsRepository
            .getMaxPage()
            .flatMapMaybe { maxPage -> popularFilmsRepository.getFilms(maxPage + 1) }

}