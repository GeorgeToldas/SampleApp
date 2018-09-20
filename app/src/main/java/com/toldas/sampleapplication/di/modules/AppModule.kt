package com.toldas.sampleapplication.di.modules

import android.app.Application
import android.content.Context
import com.toldas.sampleapplication.di.annotations.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @ApplicationContext
    internal fun provideContext(application: Application): Context {
        return application
    }

}