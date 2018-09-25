package com.toldas.sampleapplication.utils

import android.location.Location
import java.util.*

object LocationUtils {

    internal fun setDistance(currentLatitude: Double, currentLongitude: Double, latitude: Double, longitude: Double): Float {
        val distance = FloatArray(3)
        Location.distanceBetween(currentLatitude, currentLongitude, latitude, longitude, distance)
        return distance[0]
    }

    internal fun filterDistance(distance: Float): String {
        return if (distance >= 1000) {
            String.format(Locale.US, "%.2f km Away", distance / 1000)
        } else {
            String.format(Locale.US, "%d m Away", Math.round(distance))
        }
    }

    internal fun filterDistanceShort(distance: Float): String {
        return if (distance >= 1000) {
            String.format(Locale.US, "%.2f km", distance / 1000)
        } else {
            String.format(Locale.US, "%d m", Math.round(distance))
        }
    }
}