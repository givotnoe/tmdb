package io.stoptalking.pet_tmdb._presentation.presenters.base

import io.stoptalking.pet_tmdb._presentation.view.base.MvpView

interface Presenter {
    fun attach (view : MvpView)
    fun detach (view : MvpView)
}