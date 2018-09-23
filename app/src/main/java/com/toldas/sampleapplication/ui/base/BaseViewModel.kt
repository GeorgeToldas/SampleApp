package com.toldas.sampleapplication.ui.base

import android.arch.lifecycle.ViewModel
import com.toldas.sampleapplication.data.ApiService
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel
@Inject constructor(
        private val apiService: ApiService,
        private val subscription: CompositeDisposable,
        private val schedulers: SchedulerProvider
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}

