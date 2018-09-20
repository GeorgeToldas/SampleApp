package com.toldas.sampleapplication.di.components

import android.app.Application
import com.toldas.sampleapplication.SampleApp
import com.toldas.sampleapplication.data.ApiService
import com.toldas.sampleapplication.di.modules.ApiModule
import com.toldas.sampleapplication.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    fun inject(app: SampleApp)

    fun apiService(): ApiService

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun api(apiModule: ApiModule): Builder

        fun build(): AppComponent
    }

}