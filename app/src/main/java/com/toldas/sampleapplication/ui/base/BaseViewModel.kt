package com.toldas.sampleapplication.ui.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.toldas.sampleapplication.SampleApp
import com.toldas.sampleapplication.data.ApiService
import com.toldas.sampleapplication.di.components.DaggerViewModelInjector
import com.toldas.sampleapplication.di.components.ViewModelInjector
import com.toldas.sampleapplication.di.modules.RxModule
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import com.toldas.sampleapplication.ui.main.MainActivityViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var subscription: CompositeDisposable
    @Inject
    lateinit var schedulers: SchedulerProvider

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .appComponent((application as SampleApp).appComponent)
            .utils(RxModule)
            .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainActivityViewModel -> injector.inject(this)
        }
    }

}