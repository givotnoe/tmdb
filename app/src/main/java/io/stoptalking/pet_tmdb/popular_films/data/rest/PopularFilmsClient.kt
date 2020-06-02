package io.stoptalking.pet_tmdb.popular_films.data.rest

import io.reactivex.Single
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularFilmsClient {

    @GET("movie/popular")
    fun getItems(
        @Query("page") page : Int,
        @Query("api_key") key : String) : Single<PopularFilmsResponse>
}