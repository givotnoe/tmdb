package io.stoptalking.pet_tmdb._presentation.view.base

import android.os.Bundle
import androidx.annotation.CallSuper
import io.stoptalking.pet_tmdb._presentation.presenters.base.Presenter
import javax.inject.Inject

abstract class BaseMvpDialogFragment<L : BaseDialogFragment.Listener> : BaseDialogFragment<L>(),
    MvpView {

    //presenter

    private lateinit var presenters: Map<String, Presenter>

    //fragment

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (presenter in presenters.values) {
            presenter.attach(this)
        }

    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        for (presenter in presenters.values) {
            presenter.detach(this)
        }

    }

    //presenter

    @Inject
    fun setPresenters(presenters: Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Presenter>) {
        this.presenters = presenters
    }
}