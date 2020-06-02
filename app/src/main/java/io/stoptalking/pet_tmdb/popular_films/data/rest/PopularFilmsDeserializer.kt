package io.stoptalking.pet_tmdb.popular_films.data.rest

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmResponse
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsResponse
import java.lang.reflect.Type

class PopularFilmsDeserializer : JsonDeserializer<PopularFilmsResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PopularFilmsResponse {

        if (json == null || !json.isJsonObject) {
            return PopularFilmsResponse.default()
        }

        val obj = json.asJsonObject
        val res = PopularFilmsResponse.default()

        //items
        var elem = obj.get("results")
        if (elem != null && elem.isJsonArray) {

            val items = elem.asJsonArray

            for (item in items) {

                context?.let {
                    res.items.add(it.deserialize(item, PopularFilmResponse::class.java))
                }

            }
        }

        return res
    }
}