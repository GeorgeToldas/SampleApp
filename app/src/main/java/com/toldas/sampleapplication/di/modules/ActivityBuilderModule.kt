package com.toldas.sampleapplication.di.modules

import com.toldas.sampleapplication.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun bindMainActivity(): MainActivity
}