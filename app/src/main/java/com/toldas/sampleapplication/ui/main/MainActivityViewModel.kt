package com.toldas.sampleapplication.ui.main

import android.arch.lifecycle.LiveData
import com.toldas.sampleapplication.data.api.ApiService
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.db.locationModel
import com.toldas.sampleapplication.rx.callback.SingleCallbackObserver
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import com.toldas.sampleapplication.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
        private val apiService: ApiService,
        private val subscription: CompositeDisposable,
        private val schedulers: SchedulerProvider
) : BaseViewModel(apiService, subscription, schedulers) {

    private val realmDb = Realm.getDefaultInstance()

    private var locationList: LiveData<RealmResults<MapLocation>>

    init {
        locationList = realmDb.locationModel().getLocations()
        if (realmDb.isEmpty) {
            loadLocations()
        }
    }

    private fun loadLocations() {
        subscription.add(apiService
                .getLocations()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(object : SingleCallbackObserver<ArrayList<MapLocation>>() {
                    override fun onResponse(response: ArrayList<MapLocation>) {
                        realmDb.locationModel().insertLocations(response)
                        locationList = realmDb.locationModel().getLocations()
                    }

                    override fun onFailure(message: String) {

                    }
                }))
    }

    fun updateLocationList(latitude: Double, longitude: Double) {
        realmDb.locationModel().updateLocationDistance(latitude, longitude)
    }

    fun getLocationList(): LiveData<RealmResults<MapLocation>> {
        return locationList
    }
}