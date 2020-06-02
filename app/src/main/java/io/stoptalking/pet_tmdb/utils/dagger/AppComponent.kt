package io.stoptalking.pet_tmdb.utils.dagger

import android.app.Application
import com.google.gson.Gson
import dagger.Component
import io.realm.RealmConfiguration
import io.stoptalking.pet_tmdb._data.realm.RealmClient
import io.stoptalking.pet_tmdb._data.repositories.AuthKeyRepository
import io.stoptalking.pet_tmdb._data.repositories.AuthKeyRepositoryImpl
import io.stoptalking.pet_tmdb._presentation.presenters.base.SchedulerProvider
import io.stoptalking.pet_tmdb.utils.RequestsManager
import io.stoptalking.pet_tmdb.utils.dagger.modules.*
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [

    GsonModule::class,
    AndroidModule::class,
    FileModule::class,
    RetrofitModule::class,
    SchedulerModule::class,
    RealmModule::class,

    AuthKeyRepositoryImpl::class,
    RequestsManagerModule::class
])

interface AppComponent {

    fun getApplication() : Application

    //gson
    fun getGson(): Gson

    //realm
    fun getRealmConfiguration() : RealmConfiguration
    fun getRealmClient(): RealmClient

    //retrofit
    fun getRetrofit () : Retrofit

    //auth key
    fun getRequestsManager() : RequestsManager
    fun getAuthKeyRepository() : AuthKeyRepository

    //rx
    fun getSchedulerProvider() : SchedulerProvider
}