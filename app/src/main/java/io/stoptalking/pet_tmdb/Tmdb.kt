package io.stoptalking.pet_tmdb

import androidx.multidex.MultiDexApplication
import io.reactivex.plugins.RxJavaPlugins
import io.stoptalking.pet_tmdb.utils.dagger.AppComponent
import io.stoptalking.pet_tmdb.utils.dagger.DaggerAppComponent
import io.stoptalking.pet_tmdb.utils.dagger.modules.AndroidModule


class Tmdb : MultiDexApplication() {

    lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()

        //rx java
        RxJavaPlugins.setErrorHandler {}

        //component
        component = DaggerAppComponent
                .builder()
                .androidModule(AndroidModule(this))
                .build()

    }
}