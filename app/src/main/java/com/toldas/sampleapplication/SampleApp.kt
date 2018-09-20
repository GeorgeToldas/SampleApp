package com.toldas.sampleapplication

import android.app.Application
import com.toldas.sampleapplication.di.components.AppComponent
import com.toldas.sampleapplication.di.components.DaggerAppComponent
import com.toldas.sampleapplication.di.modules.ApiModule

class SampleApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .application(this)
                .api(ApiModule)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}