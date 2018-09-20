package com.toldas.sampleapplication.di.components

import com.toldas.sampleapplication.di.annotations.ViewModelScope
import com.toldas.sampleapplication.di.modules.RxModule
import com.toldas.sampleapplication.ui.main.MainActivityViewModel
import dagger.Component

@ViewModelScope
@Component(dependencies = [AppComponent::class], modules = [RxModule::class])
interface ViewModelInjector {

    fun inject(MainActivityViewModel: MainActivityViewModel)

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun utils(rxModule: RxModule): Builder
        fun build(): ViewModelInjector
    }

}