package io.stoptalking.pet_tmdb.popular_films.data.rest

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmResponse
import java.lang.reflect.Type

class PopularFilmDeserializer : JsonDeserializer<PopularFilmResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PopularFilmResponse {

        if (json == null || !json.isJsonObject) {
            return PopularFilmResponse.default()
        }

        val obj = json.asJsonObject
        val res = PopularFilmResponse.default()

        //uuid
        var elem = obj.get("id")
        if (elem != null && elem.isJsonPrimitive) {
            res.uuid = elem.asInt
        }

        //title
        elem = obj.get("title")
        if (elem != null && elem.isJsonPrimitive) {
            res.title = elem.asString
        }

        //image
        elem = obj.get("release_date")
        if (elem != null && elem.isJsonPrimitive) {
            res.date = elem.asString
        }

        //image
        elem = obj.get("poster_path")
        if (elem != null && elem.isJsonPrimitive) {
            res.image = elem.asString
        }

        //rating
        elem = obj.get("vote_average")
        if (elem != null && elem.isJsonPrimitive) {
            res.rating = elem.asDouble
        }

        return res
    }
}