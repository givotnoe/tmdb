package io.stoptalking.pet_tmdb.utils.dagger.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import io.stoptalking.pet_tmdb.data.realm.Migration
import javax.inject.Singleton

@Module
class RealmModule {

    @Provides
    @Singleton
    fun provideConfiguration (application : Application) : RealmConfiguration {
        
        Realm.init(application.applicationContext)

        return RealmConfiguration.Builder()
                .schemaVersion(Migration.getSchemaVersion())
                .migration(Migration())
                .build()
    }
}