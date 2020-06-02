package io.stoptalking.pet_tmdb.film_details.data.rest

import io.reactivex.Single
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FilmDetailsClient {

    @GET("movie/{uuid}")
    fun getDetails(
        @Path("uuid") uuid : Int,
        @Query("api_key") key : String) : Single<FilmDetailsResponse>
}