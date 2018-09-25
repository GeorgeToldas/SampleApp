package com.toldas.sampleapplication.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.toldas.sampleapplication.di.annotations.ViewModelKey
import com.toldas.sampleapplication.di.factory.ViewModelFactory
import com.toldas.sampleapplication.ui.create.CreateViewModel
import com.toldas.sampleapplication.ui.details.DetailsViewModel
import com.toldas.sampleapplication.ui.edit.EditViewModel
import com.toldas.sampleapplication.ui.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("Unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsFragmentViewHolder(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateViewModel::class)
    abstract fun bindCreateFragmentViewModel(createViewModel: CreateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel::class)
    abstract fun bindEditFragmentViewModel(editViewModel: EditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}