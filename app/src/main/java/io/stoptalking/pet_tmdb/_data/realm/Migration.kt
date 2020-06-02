package io.stoptalking.pet_tmdb._data.realm

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {

    companion object {
        fun getSchemaVersion () : Long = 0
    }

    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {

        val schema = realm?.schema
        var myOldVersion = oldVersion

//        if (myOldVersion == 0L && myOldVersion < newVersion) {
//
//            schema
//                    ?.get("CartProductEntity")
//                    ?.addRealmListField("ingredients", schema.get("CartIngredientEntity"))
//
//            schema
//                    ?.get("OrderProductItem")
//                    ?.addRealmListField("ingredients", schema.get("OrderIngredient"))
//
//            myOldVersion++
//
//        }
    }
}