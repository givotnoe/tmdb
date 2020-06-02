package io.stoptalking.pet_tmdb.utils.dagger.modules

import dagger.Module
import dagger.Provides
import io.stoptalking.pet_tmdb._data.repositories.AuthKeyRepository
import io.stoptalking.pet_tmdb.utils.RequestsManager
import javax.inject.Singleton

@Module
class RequestsManagerModule {

    @Provides
    @Singleton
    fun provideRequestsManager (authKeyRepository: AuthKeyRepository) : RequestsManager = RequestsManager(authKeyRepository)
}