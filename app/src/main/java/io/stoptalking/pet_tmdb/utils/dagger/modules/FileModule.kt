package io.stoptalking.pet_tmdb.utils.dagger.modules

import android.app.Application
import android.content.Context.MODE_PRIVATE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FileModule {

    @Provides
    @Singleton
    fun providePreferencesClient (application : Application) =
        application.getSharedPreferences("prefs", MODE_PRIVATE)
}