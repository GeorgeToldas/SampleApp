package com.toldas.sampleapplication.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.db.locationModel
import io.realm.Realm

class LocationItemViewModel : ViewModel() {

    private val realmDb = Realm.getDefaultInstance()

    private val id = MutableLiveData<Long>()
    private val latitude = MutableLiveData<Double>()
    private val longitude = MutableLiveData<Double>()
    private val address = MutableLiveData<String>()
    private val label = MutableLiveData<String>()
    private val distance = MutableLiveData<Float>()

    fun bind(mapLocation: MapLocation) {
        id.value = mapLocation.id
        latitude.value = mapLocation.latitude
        longitude.value = mapLocation.longitude
        address.value = mapLocation.address
        label.value = mapLocation.label
        distance.value = mapLocation.distance
    }

    fun getLabel(): MutableLiveData<String> {
        return label
    }

    fun getDistance(): MutableLiveData<Float> {
        return distance
    }

    fun onDeleteClick() {
        realmDb.locationModel().removeLocation(id.value!!)
    }


}