package io.stoptalking.pet_tmdb.film_details.data.rest

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsResponse
import java.lang.reflect.Type

class FilmDetailsDeserializer : JsonDeserializer<FilmDetailsResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FilmDetailsResponse {

        if (json == null || !json.isJsonObject) {
            return FilmDetailsResponse.default()
        }

        val obj = json.asJsonObject
        val res = FilmDetailsResponse.default()

        //uuid
        var elem = obj.get("id")
        if (elem != null && elem.isJsonPrimitive) {
            res.uuid = elem.asInt
        }

        //image
        elem = obj.get("poster_path")
        if (elem != null && elem.isJsonPrimitive) {
            res.image = elem.asString
        }

        //image
        elem = obj.get("overview")
        if (elem != null && elem.isJsonPrimitive) {
            res.about = elem.asString
        }

        //rating
        elem = obj.get("vote_average")
        if (elem != null && elem.isJsonPrimitive) {
            res.rating = elem.asDouble
        }

        //rating
        elem = obj.get("vote_count")
        if (elem != null && elem.isJsonPrimitive) {
            res.votesCount = elem.asInt
        }

        return res
    }
}