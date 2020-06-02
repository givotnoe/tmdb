package io.stoptalking.pet_tmdb.utils.dagger.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import io.stoptalking.pet_tmdb._data.realm.Migration
import javax.inject.Singleton

@Module
class RealmModule {

    @Provides
    @Singleton
    fun provideConfiguration (application : Application) : RealmConfiguration {
        
        Realm.init(application.applicationContext)

        return RealmConfiguration.Builder()
                .schemaVersion(Migration.getSchemaVersion())
                .deleteRealmIfMigrationNeeded()
                .build()
    }
}