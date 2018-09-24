package com.toldas.sampleapplication.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.toldas.sampleapplication.data.model.MapLocation

class LocationItemViewModel : ViewModel() {

    private val latitude = MutableLiveData<Double>()
    private val longitude = MutableLiveData<Double>()
    private val address = MutableLiveData<String>()
    private val label = MutableLiveData<String>()
    private val distance = MutableLiveData<Float>()

    fun bind(mapLocation: MapLocation) {
        latitude.value = mapLocation.latitude
        longitude.value = mapLocation.longitude
        address.value = mapLocation.address
        label.value = mapLocation.label
        distance.value = mapLocation.distance
    }
}