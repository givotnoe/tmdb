package io.stoptalking.pet_tmdb._presentation.view.base

interface NetworkMvpView : MvpView {
    fun onNetworkServerError (requestCode: String, message : String?)
    fun onNetworkIOError (requestCode: String)
}