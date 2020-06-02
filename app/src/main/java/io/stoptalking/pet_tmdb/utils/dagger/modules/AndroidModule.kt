package io.stoptalking.pet_tmdb.utils.dagger.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule (val application: Application) {

    @Provides
    @Singleton
    fun provideApp () = application

}