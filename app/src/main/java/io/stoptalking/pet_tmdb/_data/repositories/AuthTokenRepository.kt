package io.stoptalking.pet_tmdb._data.repositories

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import javax.inject.Singleton

interface AuthKeyRepository {
    fun getKey() : Single<String>
}

@Module
class AuthKeyRepositoryImpl {

    @Provides
    @Singleton
    fun provide () = object : AuthKeyRepository {

        override fun getKey(): Single<String> = Single.just("b0c65260b5fbdfb0af2eceac40010dc8")
    }
}