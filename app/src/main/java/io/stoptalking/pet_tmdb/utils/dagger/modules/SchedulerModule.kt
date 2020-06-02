package io.stoptalking.pet_tmdb.utils.dagger.modules

import dagger.Module
import dagger.Provides
import io.stoptalking.pet_tmdb._presentation.presenters.base.SchedulerProvider
import io.stoptalking.pet_tmdb._presentation.presenters.base.SchedulerProviderImpl
import javax.inject.Singleton

@Module
class SchedulerModule() {

    @Provides
    @Singleton
    fun provide () : SchedulerProvider = SchedulerProviderImpl()
}
