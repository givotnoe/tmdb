package io.stoptalking.pet_tmdb._presentation.view.base

import android.os.Bundle
import androidx.annotation.CallSuper
import io.stoptalking.pet_tmdb._presentation.presenters.base.Presenter
import javax.inject.Inject

abstract class BaseMvpActivity : BaseActivity(),
    MvpView {

    private lateinit var presenters: Map<String, Presenter>

    @CallSuper
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        for (presenter in presenters.values) {
            presenter.attach(this)
        }

    }

    @CallSuper
    public override fun onDestroy() {

        for (presenter in presenters.values) {
            presenter.detach(this)
        }

        super.onDestroy()
    }

    //presenter

    @Inject
    fun setPresenters(presenters: Map<@JvmSuppressWildcards String, @JvmSuppressWildcards Presenter>) {
        this.presenters = presenters
    }
}
