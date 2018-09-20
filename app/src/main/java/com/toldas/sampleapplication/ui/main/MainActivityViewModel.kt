package com.toldas.sampleapplication.ui.main

import android.app.Application
import android.location.Location
import com.toldas.sampleapplication.rx.callback.SingleCallbackObserver
import com.toldas.sampleapplication.ui.base.BaseViewModel

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    init {
        loadLocations()
    }

    private fun loadLocations() {
        subscription.add(apiService.getLocations()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribeWith(object : SingleCallbackObserver<ArrayList<Location>>() {
                    override fun onResponse(response: ArrayList<Location>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onFailure(message: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }))
    }

    private fun filterLocations() {

    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
        subscription.dispose()
    }
}