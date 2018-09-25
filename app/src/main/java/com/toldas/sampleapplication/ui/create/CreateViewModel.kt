package com.toldas.sampleapplication.ui.create

import android.arch.lifecycle.MutableLiveData
import android.text.Editable
import com.toldas.sampleapplication.data.api.ApiService
import com.toldas.sampleapplication.db.locationModel
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import com.toldas.sampleapplication.ui.base.BaseViewModel
import com.toldas.sampleapplication.utils.LocationUtils
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import java.util.*
import javax.inject.Inject

class CreateViewModel
@Inject constructor(
        apiService: ApiService,
        subscription: CompositeDisposable,
        schedulers: SchedulerProvider
) : BaseViewModel(apiService, subscription, schedulers) {

    private val realmDb = Realm.getDefaultInstance()

    private val latitude = MutableLiveData<Double>()
    private val longitude = MutableLiveData<Double>()
    private val address = MutableLiveData<String>()
    private val label = MutableLiveData<String>()
    private val distance = MutableLiveData<Float>()

    fun bind(latitude: Double, longitude: Double) {
        this.latitude.value = latitude
        this.longitude.value = longitude
        address.value = ""
        label.value = ""
        distance.value = 0.0f
    }

    fun updateCurrentDistance(latitude: Double, longitude: Double) {
        distance.value = LocationUtils.setDistance(this.latitude.value!!, this.longitude.value!!, latitude, longitude)
    }

    fun updateLocation(latitude: Double, longitude: Double, currentLatitude: Double, currentLongitude: Double) {
        this.latitude.value = latitude
        this.longitude.value = longitude
        updateCurrentDistance(currentLatitude, currentLongitude)
    }

    fun saveLocation() {
        realmDb.locationModel().addLocation(
                UUID.randomUUID().mostSignificantBits,
                latitude.value!!,
                longitude.value!!,
                label.value!!,
                address.value!!,
                distance.value!!
        )
    }

    fun updateLabel(text: Editable) {
        label.value = text.toString()
    }

    fun updateAddress(text: String) {
        address.value = text
    }

    fun getLatitude(): MutableLiveData<Double> {
        return latitude
    }

    fun getLongitude(): MutableLiveData<Double> {
        return longitude
    }

    fun getAddress(): MutableLiveData<String> {
        return address
    }

    fun getLabel(): MutableLiveData<String> {
        return label
    }

    fun getDistance(): MutableLiveData<Float> {
        return distance
    }
}