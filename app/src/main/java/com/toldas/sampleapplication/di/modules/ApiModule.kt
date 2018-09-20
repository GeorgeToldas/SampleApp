package com.toldas.sampleapplication.di.modules

import android.app.Application
import com.toldas.sampleapplication.data.ApiService
import com.toldas.sampleapplication.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object ApiModule {

    @Provides
    @Singleton
    internal fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.baseURL)
                .client(okHttpClient)
                .build().create(ApiService::class.java)
    }
}