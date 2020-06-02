package io.stoptalking.pet_tmdb.utils.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.stoptalking.pet_tmdb.BuildConfig
import io.stoptalking.pet_tmdb._data.image.GlideHandler
import io.stoptalking.pet_tmdb._data.image.ImageHandler
import io.stoptalking.pet_tmdb.utils.dagger.ActivityScope

@Module
class GlideModule (private val context : Context) {

    @Provides
    @ActivityScope
    fun providePngHandler () : ImageHandler = GlideHandler(context, BuildConfig.IMAGE_BASE_URL)

}