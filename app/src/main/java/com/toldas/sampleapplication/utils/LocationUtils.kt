package com.toldas.sampleapplication.utils

import android.location.Location
import com.toldas.sampleapplication.data.model.MapLocation
import java.util.*

object LocationUtils {

    internal fun setDistance(location: MapLocation, latitude: Double, longitude: Double): Float {
        val distance = FloatArray(3)
        Location.distanceBetween(location.latitude, location.longitude, latitude, longitude, distance)
        return distance[0]
    }

    internal fun filterDistance(distance: Float): String {
        return if (distance >= 1000) {
            String.format(Locale.US, "%.2f km Away", distance / 1000)
        } else {
            String.format(Locale.US, "%d m Away", Math.round(distance))
        }

    }
}