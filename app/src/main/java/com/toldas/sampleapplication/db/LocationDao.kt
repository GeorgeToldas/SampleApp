package com.toldas.sampleapplication.db

import android.arch.lifecycle.LiveData
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.utils.LocationUtils
import io.realm.Realm
import io.realm.RealmResults

class LocationDao(val realm: Realm) {

    fun updateItem(
            id: Long,
            latitude: Double,
            longitude: Double,
            label: String,
            address: String,
            distance: Float
    ) {
        Realm.getInstance(realm.configuration)
                .executeTransaction { realm ->
                    val realmObject = realm.where(MapLocation::class.java).equalTo("id", id).findFirst()
                    realmObject?.latitude = latitude
                    realmObject?.longitude = longitude
                    realmObject?.label = label
                    realmObject?.address = address
                    realmObject?.distance = distance
                }
    }

    fun insertLocations(list: List<MapLocation>) {
        Realm.getInstance(realm.configuration)
                .executeTransaction { realm ->
                    for (item in list) {
                        realm.insertOrUpdate(item)
                    }
                }
    }

    fun updateLocationDistance(latitude: Double, longitude: Double) {
        Realm.getInstance(realm.configuration)
                .executeTransaction { realm ->
                    val realmData = realm.where(MapLocation::class.java).findAllAsync()
                    for (realmObject in realmData) {
                        realmObject.distance = LocationUtils.setDistance(realmObject.latitude, realmObject.longitude, latitude, longitude)
                    }
                }
    }

    fun removeLocation(id: Long) {
        Realm.getInstance(realm.configuration)
                .executeTransaction { realm ->
                    val realmObject = realm.where(MapLocation::class.java).equalTo("id", id).findFirst()
                    realmObject?.deleteFromRealm()
                }
    }

    fun getLocations(): LiveData<RealmResults<MapLocation>> {
        return realm.where(MapLocation::class.java).findAllAsync().asLiveData()
    }
}