package io.stoptalking.pet_tmdb.popular_films.dagger

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb._presentation.view.base.adapter.PaginationScrollBehavior
import io.stoptalking.pet_tmdb._presentation.view.base.adapter.PaginationScrollBehavior.Companion.THRESHOLD_10
import io.stoptalking.pet_tmdb.popular_films.presenters.POPULAR_FILMS_NEXT_PAGE
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import javax.inject.Named

@Module
class PopularFilmsModule {

    //adapter

    @Provides
    @ViewScope
    fun provideLayoutManager (context: Context) =
        LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    @Provides
    @ViewScope
    fun provideScrollBehavior(layoutManager: LinearLayoutManager,
                              @Named(POPULAR_FILMS_NEXT_PAGE) subject : Subject<Event>) =
        PaginationScrollBehavior(layoutManager, subject, THRESHOLD_10)


}
