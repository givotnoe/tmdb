package io.stoptalking.pet_tmdb.film_details.data.repositories

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import io.stoptalking.pet_tmdb._data.realm.RealmClient
import io.stoptalking.pet_tmdb.film_details.data.rest.FilmDetailsClient
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsDTO
import io.stoptalking.pet_tmdb.film_details.data.FilmDetailsItem
import io.stoptalking.pet_tmdb.utils.RequestsManager
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import retrofit2.Retrofit

interface FilmDetailsRepository {
    fun refreshDetails(uuid : Int) : Single<FilmDetailsDTO>
    fun getDetails(uuid : Int) : Single<FilmDetailsDTO>
}

@Module
class FilmDetailsRepositoryNetwork {

    @Provides
    @ViewScope
    fun provideClient (retrofit: Retrofit) : FilmDetailsClient = retrofit.create(
        FilmDetailsClient::class.java)

    @Provides
    @ViewScope
    fun provide (realmClient: RealmClient,
                 client : FilmDetailsClient,
                 requestsManager: RequestsManager) = object :
        FilmDetailsRepository {

        override fun refreshDetails(uuid : Int): Single<FilmDetailsDTO> =
            requestsManager
                .executeSingle { key -> client.getDetails(uuid, key) }
                .flatMap { response ->
                    realmClient
                        .executeCompletable { realm ->

                            val item = realm
                                .where(FilmDetailsItem::class.java)
                                .equalTo("uuid", uuid)
                                .findFirst()

                            item?.delete()

                            val nItem = FilmDetailsItem.fromResponse(response)
                            realm.copyToRealm(nItem)

                            return@executeCompletable
                        }
                        .toSingleDefault(FilmDetailsDTO.fromResponse(response))
                }

        override fun getDetails(uuid: Int): Single<FilmDetailsDTO> =
            realmClient
                .executeMaybe<FilmDetailsDTO> { realm ->

                    val item = realm
                        .where(FilmDetailsItem::class.java)
                        .equalTo("uuid", uuid)
                        .findFirst()

                    item?.let { return@executeMaybe FilmDetailsDTO.fromEntity(it) }
                    return@executeMaybe null
                }
                .switchIfEmpty(refreshDetails(uuid))
    }
}