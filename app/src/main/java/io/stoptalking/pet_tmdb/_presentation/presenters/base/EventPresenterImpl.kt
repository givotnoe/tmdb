package io.stoptalking.pet_tmdb._presentation.presenters.base

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import io.stoptalking.pet_tmdb._presentation.view.base.MvpView

abstract class EventPresenterImpl<K : MvpView, Upstream, Downstream>(protected val requestCode: String,
                                                                     protected val errorBehavior: ErrorBehavior<K>,
                                                                     protected val schedulerProvider : SchedulerProvider,
                                                                     protected val subject : Subject<Upstream>) :
    Presenter {

    protected abstract val obs : Observable<Downstream>

    protected var view : K? = null
    private lateinit var disposable : Disposable

    override fun attach(view: MvpView) {

        this.view = view as K?

        disposable = obs
            .observeOn(schedulerProvider.getUI())
            .doOnError { errorBehavior.onError(it, requestCode, view) }
            .retry()
            .subscribe({}, {}, {})

    }

    override fun detach(view: MvpView) {

        if (this.view === view) {

            this.view = null
            
            disposable.dispose()
            subject.onComplete()
        }
    }
}