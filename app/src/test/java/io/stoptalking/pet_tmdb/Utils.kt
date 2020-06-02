package io.stoptalking.pet_tmdb

import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb._presentation.presenters.base.SchedulerProviderTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

val schedulerProvider = SchedulerProviderTest()
val httpException = HttpException(
    Response.error<List<PopularFilmsDTO>>(
        500,
        ResponseBody.create(
            MediaType.parse("application/json"),
            "{\"error\":[\"Internal server error\"]}"
        )
    )
)