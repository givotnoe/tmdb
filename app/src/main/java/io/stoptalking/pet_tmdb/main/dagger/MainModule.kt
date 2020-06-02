package io.stoptalking.pet_tmdb.main.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._data.models.Event
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope

@Module
class MainModule {

    //subject

    @Provides
    @ViewScope
    fun provideSubject () : Subject<Event> =
        PublishSubject.create()

}