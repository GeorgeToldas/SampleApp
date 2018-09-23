package com.toldas.sampleapplication.data

import android.location.Location
import com.toldas.sampleapplication.data.model.MapLocation
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiService {

    @GET("GetLocations")
    fun getLocations(): Single<ArrayList<MapLocation>>
}