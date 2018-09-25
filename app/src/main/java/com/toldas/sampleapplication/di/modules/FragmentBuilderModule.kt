package com.toldas.sampleapplication.di.modules

import com.toldas.sampleapplication.ui.edit.EditFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeEditFragment(): EditFragment

}