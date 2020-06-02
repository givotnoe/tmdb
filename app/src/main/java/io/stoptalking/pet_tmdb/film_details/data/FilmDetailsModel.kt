package io.stoptalking.pet_tmdb.film_details.data

import io.realm.RealmObject
import io.stoptalking.pet_tmdb._data.models.CompoundModel
import io.stoptalking.pet_tmdb.utils.DEFAULT_DOUBLE
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT
import io.stoptalking.pet_tmdb.utils.DEFAULT_STRING

//dto

data class FilmDetailsDTO (val uuid : Int,
                      val image : String,
                      val about : String,
                      val rating : Double,
                      val votesCount : Int) {

    companion object {

        fun default() =
            FilmDetailsDTO(
                DEFAULT_INT,
                DEFAULT_STRING,
                DEFAULT_STRING,
                DEFAULT_DOUBLE,
                DEFAULT_INT
            )

        fun fromResponse(response : FilmDetailsResponse) =
            FilmDetailsDTO(
                response.uuid,
                response.image,
                response.about,
                response.rating,
                response.votesCount
            )

        fun fromEntity(entity : FilmDetailsItem) =
            FilmDetailsDTO(
                entity.uuid,
                entity.image,
                entity.about,
                entity.rating,
                entity.votesCount
            )

    }
}

//response

data class FilmDetailsResponse (var uuid : Int,
                                var image : String,
                                var about : String,
                                var rating : Double,
                                var votesCount : Int) {

    companion object {

        fun default() =
            FilmDetailsResponse(
                DEFAULT_INT,
                DEFAULT_STRING,
                DEFAULT_STRING,
                DEFAULT_DOUBLE,
                DEFAULT_INT
            )
    }

}

//entity

open class FilmDetailsItem(var uuid : Int,
                           var image : String,
                           var about : String,
                           var rating : Double,
                           var votesCount : Int) : RealmObject(),
    CompoundModel {

    constructor() : this(DEFAULT_INT, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_DOUBLE, DEFAULT_INT)

    companion object {

        fun fromResponse(response : FilmDetailsResponse) =
            FilmDetailsItem(
                response.uuid,
                response.image,
                response.about,
                response.rating,
                response.votesCount
            )
    }

    //CompoundModel

    override fun delete() {
        deleteFromRealm()
    }
}