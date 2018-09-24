package com.toldas.sampleapplication.di.modules

import com.toldas.sampleapplication.rx.schedulers.AppSchedulerProvider
import com.toldas.sampleapplication.rx.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Suppress("Unused")
@Module
class RxModule {

    @Provides
    internal fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}