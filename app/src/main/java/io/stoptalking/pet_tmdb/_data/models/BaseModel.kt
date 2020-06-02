package io.stoptalking.pet_tmdb._data.models

interface Event {
    companion object : Event
}

interface CompoundModel {
    fun delete()
}