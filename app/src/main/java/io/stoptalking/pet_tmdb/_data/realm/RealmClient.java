package io.stoptalking.pet_tmdb._data.realm;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.stoptalking.pet_tmdb._data.models.CompoundModel;
import kotlin.Unit;

/**
 * Created by tdbes on 07.06.2017.
 */
@Singleton
public class RealmClient {

    private RealmConfiguration realmConfig;

    @Inject
    public RealmClient(RealmConfiguration realmConfig) {
        this.realmConfig = realmConfig;
    }

    //clear methods

    @SafeVarargs
    public final Completable clearRealmExcept(final Class<? extends RealmModel>... exceptions) {

        return Completable.fromCallable(() -> {

            Realm   realm = Realm.getInstance(realmConfig);
            List<?> exList = Arrays.asList(exceptions);

            realm.executeTransaction(r -> {

                Set<Class<? extends RealmModel>> classes = realmConfig.getRealmObjectClasses();

                for (Class<? extends RealmModel> tClass : classes) {

                    if (!exList.contains(tClass)) {
                        r.delete(tClass);
                    }
                }
            });

            realm.close();

            return Unit.INSTANCE;
        });
    }

    public <T extends RealmModel> Completable clearRealm(final Class<T> tClass) {

        return Completable.fromCallable(() -> {

            Realm realm = Realm.getInstance(realmConfig);
            realm.executeTransaction(r -> r.where(tClass).findAll().deleteAllFromRealm());
            realm.close();

            return Unit.INSTANCE;

        });
    }

    //delete

    public <T extends RealmObject & CompoundModel> Completable deleteCascade(final Class<T> tClass) {

        return Completable
                .fromCallable(() -> {

                    Realm realm = Realm.getInstance(realmConfig);

                    realm.executeTransaction(r -> {

                        RealmResults<T> results = r.where(tClass).findAll();

                        for (T item : results) {
                            item.delete();
                        }

                    });

                    realm.close();

                    return Unit.INSTANCE;
                });
    }

    //common

    public <T> Observable<T> executeObservable (RealmRunnable<T> executable) {

        return Observable
                .fromCallable(() -> {

                    Realm realm = Realm.getInstance(realmConfig);

                    realm.beginTransaction();
                    T res = executable.execute(realm);
                    realm.commitTransaction();

                    realm.close();

                    return res;
                });
    }

    public <T> Single<T> executeSingle (RealmRunnable<T> executable) {

        return Single
                .fromCallable(() -> {

                    Realm realm = Realm.getInstance(realmConfig);

                    realm.beginTransaction();
                    T res = executable.execute(realm);
                    realm.commitTransaction();

                    realm.close();

                    return res;
                });
    }

    public <T> Maybe<T> executeMaybe (RealmRunnable<T> executable) {

        return Maybe
                .fromCallable(() -> {

                    Realm realm = Realm.getInstance(realmConfig);

                    realm.beginTransaction();
                    T res = executable.execute(realm);
                    realm.commitTransaction();

                    realm.close();

                    return res;
                });
    }

    public <T> Completable executeCompletable (RealmRunnable<T> executable) {

        return Completable
                .fromCallable(() -> {

                    Realm realm = Realm.getInstance(realmConfig);

                    realm.beginTransaction();
                    T res = executable.execute(realm);
                    realm.commitTransaction();

                    realm.close();

                    return res;
                });
    }

    public interface RealmRunnable<T> {
        T execute(Realm realm);
    }

    //sync

    public <T extends RealmModel> T getFirstSync (Class<T> cls) {

        T res = null;
        T entity;

        Realm realm = Realm.getInstance(realmConfig);
        entity = realm.where(cls).findFirst();

        if (entity != null) {
            res = realm.copyFromRealm(entity);
        }

        realm.close();

        return res;
    }
}
