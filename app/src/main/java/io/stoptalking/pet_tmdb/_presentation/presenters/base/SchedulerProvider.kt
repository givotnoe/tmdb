package io.stoptalking.pet_tmdb._presentation.presenters.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun getIO() : Scheduler
    fun getUI() : Scheduler
}

class SchedulerProviderImpl : SchedulerProvider {

    override fun getIO(): Scheduler = Schedulers.io()

    override fun getUI(): Scheduler = AndroidSchedulers.mainThread()
}

class SchedulerProviderTest : SchedulerProvider {

    override fun getIO(): Scheduler = Schedulers.trampoline()

    override fun getUI(): Scheduler = Schedulers.trampoline()
}
