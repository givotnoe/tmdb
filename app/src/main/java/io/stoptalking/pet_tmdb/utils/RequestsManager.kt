package io.stoptalking.pet_tmdb.utils

import io.reactivex.Completable
import io.reactivex.Single
import io.stoptalking.pet_tmdb._data.repositories.AuthKeyRepository

class RequestsManager (private val authKeyRepository: AuthKeyRepository) {

    fun <T> executeSingle (call: (key : String) -> Single<T>) : Single<T> =
        authKeyRepository
            .getKey()
            .flatMap (call)

    fun executeCompletable (call: (key : String) -> Completable) : Completable =
        authKeyRepository
            .getKey()
            .flatMapCompletable (call)
}