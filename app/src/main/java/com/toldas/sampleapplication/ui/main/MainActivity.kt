package com.toldas.sampleapplication.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.databinding.ActivityMainBinding
import com.toldas.sampleapplication.ui.base.BaseActivity
import com.toldas.sampleapplication.ui.listeners.ItemClickListener
import com.toldas.sampleapplication.utils.PermissionUtils
import io.realm.RealmResults
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private var hasLocationPermission: Boolean = false

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLocation: Location

    private lateinit var adapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel

        initLocationServices()
        initAdapter()
    }

    private fun initLocationServices() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                currentLocation = locationResult.lastLocation
                viewModel.updateLocationList(currentLocation.latitude, currentLocation.longitude)
            }
        }
        hasLocationPermission = PermissionUtils.checkLocationSettings(this)
        if (hasLocationPermission) {
            setLocationProvider()
        }
    }

    private fun initAdapter() {
        binding.locationRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val onClickListener = object : ItemClickListener<MapLocation> {
            override fun onClick(item: MapLocation) {

            }
        }
        adapter = LocationAdapter(onClickListener)
        viewModel.getLocationList().observe(this, Observer<RealmResults<MapLocation>> { locations -> adapter.setList(locations) })


    }

    @SuppressLint("MissingPermission")
    private fun setLocationProvider() {
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()
                .setInterval(60000)
                .setFastestInterval(60000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        hasLocationPermission = true
                        setLocationProvider()
                    }
                } else {
                    hasLocationPermission = PermissionUtils.checkLocationSettings(this)
                }
            }
        }
    }


}