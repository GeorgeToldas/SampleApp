package com.toldas.sampleapplication.data.api

import com.toldas.sampleapplication.data.model.MapLocation
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("GetLocations")
    fun getLocations(): Single<ArrayList<MapLocation>>
}