package io.stoptalking.pet_tmdb._presentation.presenters.base

import io.stoptalking.pet_tmdb._presentation.view.base.MvpView
import io.stoptalking.pet_tmdb._presentation.view.base.NetworkMvpView
import retrofit2.HttpException
import java.io.IOException

interface ErrorBehavior<T : MvpView> {
    fun onError(e: Throwable?, requestCode : String, view : T?)
}

class SimpleBehavior<T : MvpView> : ErrorBehavior<T> {

    override fun onError(e: Throwable?, requestCode : String, view : T?) {

        e?.printStackTrace()
        view?.onUnknownError(requestCode)
    }
}

open class ErrorBehaviorDecorator<T : MvpView>(private val errorBehavior : ErrorBehavior<T>) : ErrorBehavior<T> {

    override fun onError(e: Throwable?, requestCode : String, view : T?) {
        errorBehavior.onError(e, requestCode, view)
    }
}

class RetrofitErrorBehavior<T : NetworkMvpView>(errorBehavior: ErrorBehavior<T>) : ErrorBehaviorDecorator<T>(errorBehavior) {

    override fun onError(e: Throwable?, requestCode : String, view : T?) {

        if (e is IOException) {

            e.printStackTrace()
            view?.onNetworkIOError(requestCode)

            return
        }

        if (e is HttpException) {

            e.printStackTrace()
            view?.onNetworkServerError(requestCode, e.message)

            return
        }

        super.onError(e, requestCode, view)
    }
}