package com.toldas.sampleapplication.di.components

import android.app.Application
import com.toldas.sampleapplication.SampleApp
import com.toldas.sampleapplication.di.modules.ActivityBuilderModule
import com.toldas.sampleapplication.di.modules.ApplicationModule
import com.toldas.sampleapplication.di.modules.NetworkModule
import com.toldas.sampleapplication.di.modules.RxModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    RxModule::class,
    ApplicationModule::class,
    ActivityBuilderModule::class
])
interface AppComponent {

    fun inject(app: SampleApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}