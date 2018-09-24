package com.toldas.sampleapplication.db

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

fun Realm.locationModel(): LocationDao = LocationDao(this)

fun <T : RealmModel> RealmResults<T>.asLiveData() = RealmLiveData<T>(this)