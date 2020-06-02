package io.stoptalking.pet_tmdb.film_details.dagger

import android.content.Context
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import io.stoptalking.pet_tmdb.R
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Named

@Module
class FilmDetailsModule {

    @Named("blue_span")
    @Provides
    @ViewScope
    fun provideBSpan (context : Context) =
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue))

    @Named("blue_light_span")
    @Provides
    @ViewScope
    fun provideBLSpan (context : Context) =
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.blue_light_0))

}
