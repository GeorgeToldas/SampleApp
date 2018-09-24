package com.toldas.sampleapplication.utils

import android.location.Location
import com.toldas.sampleapplication.data.model.MapLocation

object LocationUtils {

    internal fun setDistance(location: MapLocation, latitude: Double, longitude: Double): Float {
        val distance = FloatArray(3)
        Location.distanceBetween(location.latitude, location.longitude, latitude, longitude, distance)
        return distance[0]
    }
}