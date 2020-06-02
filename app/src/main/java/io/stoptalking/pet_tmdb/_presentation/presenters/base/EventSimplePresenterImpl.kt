package io.stoptalking.pet_tmdb._presentation.presenters.base

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._presentation.view.base.MvpView

abstract class EventSimplePresenterImpl<K : MvpView, Upstream, Downstream>(requestCode: String,
                                                                           errorBehavior: ErrorBehavior<K>,
                                                                           schedulerProvider : SchedulerProvider,
                                                                           subject : Subject<Upstream>) :
    EventPresenterImpl<K, Upstream, Downstream>(requestCode, errorBehavior, schedulerProvider, subject) {

    protected abstract val transformer : ObservableTransformer<Upstream, Downstream>

    override val obs: Observable<Downstream>
        get() =
            subject
                .observeOn(schedulerProvider.getIO())
                .compose(transformer)
}