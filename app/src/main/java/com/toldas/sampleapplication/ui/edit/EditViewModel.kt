package com.toldas.sampleapplication.ui.edit

import android.arch.lifecycle.MutableLiveData
import com.toldas.sampleapplication.data.api.ApiService
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import com.toldas.sampleapplication.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import javax.inject.Inject

class EditViewModel
@Inject constructor(
        private val apiService: ApiService,
        private val subscription: CompositeDisposable,
        private val schedulers: SchedulerProvider
) : BaseViewModel(apiService, subscription, schedulers) {

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
}