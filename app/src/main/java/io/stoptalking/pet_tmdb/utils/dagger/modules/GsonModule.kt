package io.stoptalking.pet_tmdb.utils.dagger.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsResponse
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmResponse
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsResponse
import io.stoptalking.pet_tmdb.film_details.data.rest.FilmDetailsDeserializer
import io.stoptalking.pet_tmdb.popular_films.data.rest.PopularFilmDeserializer
import io.stoptalking.pet_tmdb.popular_films.data.rest.PopularFilmsDeserializer
import javax.inject.Singleton

@Module
class GsonModule {

    @Provides
    @Singleton
    fun provideGson () : Gson = GsonBuilder()
        .registerTypeAdapter(PopularFilmResponse::class.java,
            PopularFilmDeserializer()
        )
        .registerTypeAdapter(PopularFilmsResponse::class.java,
            PopularFilmsDeserializer()
        )
        .registerTypeAdapter(FilmDetailsResponse::class.java,
            FilmDetailsDeserializer()
        )
        .create()
}
