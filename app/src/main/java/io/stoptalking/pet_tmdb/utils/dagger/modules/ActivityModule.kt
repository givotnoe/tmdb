package io.stoptalking.pet_tmdb.utils.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.stoptalking.pet_tmdb.utils.dagger.ActivityScope

@Module
class ActivityModule(val context: Context) {

    @Provides
    @ActivityScope
    fun provideContext () = context
}
