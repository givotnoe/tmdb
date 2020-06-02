package io.stoptalking.pet_tmdb.utils.dagger

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Component
import io.realm.RealmConfiguration
import io.stoptalking.pet_tmdb._data.image.ImageHandler
import io.stoptalking.pet_tmdb._data.realm.RealmClient
import io.stoptalking.pet_tmdb._presentation.presenters.base.SchedulerProvider
import io.stoptalking.pet_tmdb.utils.RequestsManager
import io.stoptalking.pet_tmdb.utils.dagger.modules.ActivityModule
import io.stoptalking.pet_tmdb.utils.dagger.modules.GlideModule
import retrofit2.Retrofit

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [

    ActivityModule::class,
    GlideModule::class
])
interface ActivityComponent {

    //get
    fun getContext() : Context
    fun getApplication() : Application

    //image
    fun getImageHandler() : ImageHandler

    //gson
    fun getGson(): Gson

    //realm
    fun getRealmConfiguration() : RealmConfiguration
    fun getRealmClient(): RealmClient

    //retrofit
    fun getRetrofit () : Retrofit

    //auth key
    fun getRequestsManager() : RequestsManager

    //rx
    fun getSchedulerProvider() : SchedulerProvider
}