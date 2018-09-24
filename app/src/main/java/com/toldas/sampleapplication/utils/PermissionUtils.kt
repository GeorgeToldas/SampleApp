package com.toldas.sampleapplication.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.toldas.sampleapplication.R

object PermissionUtils {

    internal fun checkLocationSettings(context: Context): Boolean {
        return if (hasSystemLocationServices(context)) {
            checkLocationPermissions(context)
        } else {
            false
        }
    }

    private fun hasSystemLocationServices(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var enabledServices = false
        val gpsEnabled: Boolean
        val networkEnabled: Boolean
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!gpsEnabled && !networkEnabled) {
            AlertDialog.Builder(context)
                    .setMessage(context.resources.getString(R.string.gps_not_enabled))
                    .setPositiveButton(context.resources.getString(R.string.open_settings)) { _, _ ->
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        context.startActivity(intent)
                    }
                    .setNegativeButton(context.resources.getString(R.string.cancel_button)) { _, _ -> }
                    .show()
        }
        if (gpsEnabled && networkEnabled) {
            enabledServices = true
        }
        return enabledServices
    }

    private fun checkLocationPermissions(context: Context): Boolean {
        return if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Location permission is already granted
            true
        } else {
            requestLocationPermission(context)
            false
        }
    }

    private fun requestLocationPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(context)
                        .setTitle("Location Permission required")
                        .setMessage("This app requires the location services to be enabled")
                        .setPositiveButton("Accept") { _, _ ->
                            context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                        }
                        .create()
                        .show()
            } else {
                context.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }
}