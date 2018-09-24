package com.toldas.sampleapplication.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.toldas.sampleapplication.data.api.ApiService
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.rx.callback.SingleCallbackObserver
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import com.toldas.sampleapplication.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
        private val apiService: ApiService,
        private val subscription: CompositeDisposable,
        private val schedulers: SchedulerProvider
) : BaseViewModel(apiService, subscription, schedulers) {

    private val locationList: MutableLiveData<ArrayList<MapLocation>> = MutableLiveData()

    init {
        loadLocations()
    }

    private fun loadLocations() {
        subscription.add(apiService
                .getLocations()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(object : SingleCallbackObserver<ArrayList<MapLocation>>() {
                    override fun onResponse(response: ArrayList<MapLocation>) {
                        locationList.value = response
                    }

                    override fun onFailure(message: String) {

                    }
                }))
    }

    fun getLocationList(): LiveData<ArrayList<MapLocation>> {
        return locationList
    }
}