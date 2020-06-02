package io.stoptalking.pet_tmdb.popular_films.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.stoptalking.pet_tmdb._data.models.CompoundModel
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb.utils.DEFAULT_DOUBLE
import io.stoptalking.pet_tmdb.utils.DEFAULT_INT
import io.stoptalking.pet_tmdb.utils.DEFAULT_STRING
import io.stoptalking.pet_tmdb.utils.REALM_ID

//event

data class ShowDetailsDTO (val uuid : Int) :
    Event {

    companion object {

        fun default() =
            ShowDetailsDTO(
                DEFAULT_INT
            )

        fun from(dto : PopularFilmsDTO) =
            ShowDetailsDTO(
                dto.uuid
            )
    }
}

//dto

data class PopularFilmsDTO(val uuid : Int,
                      val title : String,
                      val image : String,
                      val date : String,
                      val rating : Double) {

    companion object {

        fun default() =
            PopularFilmsDTO(
                DEFAULT_INT,
                DEFAULT_STRING,
                DEFAULT_STRING,
                DEFAULT_STRING,
                DEFAULT_DOUBLE
            )

        fun fromResponse(response : PopularFilmResponse) =
            PopularFilmsDTO(
                response.uuid,
                response.title,
                response.image,
                response.date,
                response.rating
            )

        fun fromEntity(entity : PopularFilmsItem) =
            PopularFilmsDTO(
                entity.uuid,
                entity.title,
                entity.image,
                entity.date,
                entity.rating
            )
    }
}

//response

data class PopularFilmsResponse(val items : MutableList<PopularFilmResponse>,
                                val page : Int) {

    companion object {

        fun default() =
            PopularFilmsResponse(
                mutableListOf(),
                1
            )
    }
}

data class PopularFilmResponse(var uuid : Int,
                               var title : String,
                               var image : String,
                               var date : String,
                               var rating : Double) {

    companion object {

        fun default() =
            PopularFilmResponse(
                DEFAULT_INT,
                DEFAULT_STRING,
                DEFAULT_STRING,
                DEFAULT_STRING,
                DEFAULT_DOUBLE
            )
    }
}

//entities

open class PopularFilmsItem(var uuid : Int,
                            var title : String,
                            var image : String,
                            var date : String,
                            var rating : Double) : RealmObject(),
    CompoundModel {

    constructor() : this (DEFAULT_INT, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_DOUBLE)

    companion object {

        fun fromResponse(response : PopularFilmResponse) =
            PopularFilmsItem(
                response.uuid,
                response.title,
                response.image,
                response.date,
                response.rating
            )
    }

    //CompoundModel

    override fun delete() {
        deleteFromRealm()
    }
}

open class PopularFilmsPageItem(var maxPage : Int,
                                var lastPage : Boolean,
                                @PrimaryKey var id : Int = REALM_ID) : RealmObject(),
    CompoundModel {

    constructor() : this (
        DEFAULT_PAGE,
        DEFAULT_LAST_PAGE
    )

    companion object {

        const val DEFAULT_PAGE = 1
        const val DEFAULT_LAST_PAGE = false

    }

    //CompoundModel

    override fun delete() {
        deleteFromRealm()
    }
}