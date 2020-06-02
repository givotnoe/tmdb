package io.stoptalking.pet_tmdb.popular_films.data.repositories

import dagger.Module
import dagger.Provides
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsDTO
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsItem
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsPageItem
import io.stoptalking.pet_tmdb.popular_films.data.PopularFilmsPageItem.Companion.DEFAULT_LAST_PAGE
import io.stoptalking.pet_tmdb._data.realm.RealmClient
import io.stoptalking.pet_tmdb.popular_films.data.rest.PopularFilmsClient
import io.stoptalking.pet_tmdb.utils.RequestsManager
import io.stoptalking.pet_tmdb.utils.dagger.ViewScope
import retrofit2.Retrofit

interface PopularFilmsRepository {
    fun refreshFilms() : Single<List<PopularFilmsDTO>>
    fun getFilms(page : Int) : Maybe<List<PopularFilmsDTO>>
    fun getFilms() : Single<List<PopularFilmsDTO>>
    fun getMaxPage() : Single<Int>
}

@Module
class PopularFilmsRepositoryNetwork {

    @Provides
    @ViewScope
    fun provideClient (retrofit: Retrofit) : PopularFilmsClient = retrofit.create(
        PopularFilmsClient::class.java)

    @Provides
    @ViewScope
    fun provide (realmClient: RealmClient,
                 popularFilmsClient: PopularFilmsClient,
                 requestsManager: RequestsManager) = object :
        PopularFilmsRepository {

        override fun refreshFilms(): Single<List<PopularFilmsDTO>> =
            requestsManager
                .executeSingle { key -> popularFilmsClient.getItems(1, key) }
                .flatMap { response ->
                    realmClient
                        .clearRealm(PopularFilmsItem::class.java)
                        .andThen(
                            realmClient
                                .executeCompletable { realm ->

                                    val page =
                                        PopularFilmsPageItem()
                                    realm.copyToRealmOrUpdate(page)

                                    val items = response
                                        .items
                                        .map { PopularFilmsItem.fromResponse(it) }

                                    realm.copyToRealm(items)

                                    return@executeCompletable
                                }
                        )
                        .andThen(
                            Observable
                                .fromIterable(response.items)
                                .map { item -> PopularFilmsDTO.fromResponse(item) }
                                .toList()
                        )
                }

        override fun getFilms(page: Int): Maybe<List<PopularFilmsDTO>> =
            realmClient
                .executeSingle { realm ->

                    val curPage = realm
                        .where(PopularFilmsPageItem::class.java)
                        .findFirst()

                    curPage?.let { return@executeSingle it.lastPage }
                    return@executeSingle DEFAULT_LAST_PAGE
                }
                .flatMapMaybe { isLastPage ->
                    when (isLastPage) {
                        true -> Maybe.empty()
                        else ->
                            requestsManager
                                .executeSingle { key -> popularFilmsClient.getItems(page, key) }
                                .flatMap { response ->
                                    realmClient
                                        .executeCompletable { realm ->

                                            val curPage = realm
                                                .where(PopularFilmsPageItem::class.java)
                                                .findFirst()

                                            curPage?.apply {
                                                maxPage = page
                                                lastPage = response.items.isEmpty()
                                            }

                                            val items = response
                                                .items
                                                .map { PopularFilmsItem.fromResponse(it) }

                                            realm.copyToRealm(items)

                                            return@executeCompletable
                                        }
                                        .andThen(
                                            Observable
                                                .fromIterable(response.items)
                                                .map { item -> PopularFilmsDTO.fromResponse(item) }
                                                .toList()
                                        )
                                }
                                .toMaybe()
                    }
                }

        override fun getFilms(): Single<List<PopularFilmsDTO>> =
            realmClient
                .executeSingle { realm ->

                    realm
                        .where(PopularFilmsItem::class.java)
                        .findAll()
                        .map { PopularFilmsDTO.fromEntity(it) }

                }

        override fun getMaxPage(): Single<Int> =
            realmClient
                .executeSingle { realm ->

                    val page = realm
                        .where(PopularFilmsPageItem::class.java)
                        .findFirst()

                    page?.let { return@executeSingle it.maxPage }
                    return@executeSingle 1
                }
    }
}