package io.stoptalking.pet_tmdb.film_details.domain

import io.reactivex.Maybe
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsDTO
import io.stoptalking.pet_tmdb.film_details.data.repositories.FilmDetailsRepository
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Inject

@ViewScope
class FilmDetailsUseCase @Inject constructor(private val filmDetailsRepository: FilmDetailsRepository) {

    fun initDetails(uuid : Int?) : Maybe<FilmDetailsDTO> =
        when(uuid) {
            null -> Maybe.empty()
            else ->
                filmDetailsRepository
                    .refreshDetails(uuid)
                    .toMaybe()
        }

    fun restoreDetails(uuid : Int?) : Maybe<FilmDetailsDTO> =
        when(uuid) {
            null -> Maybe.empty()
            else ->
                filmDetailsRepository
                    .getDetails(uuid)
                    .toMaybe()
        }
}