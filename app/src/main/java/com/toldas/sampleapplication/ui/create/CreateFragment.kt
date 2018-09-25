package com.toldas.sampleapplication.ui.create


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxbinding2.view.RxView
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.databinding.FragmentCreateBinding

import com.toldas.sampleapplication.ui.base.BaseDialogFragment
import com.toldas.sampleapplication.utils.PermissionUtils
import java.io.IOException
import java.util.*
import javax.inject.Inject

class CreateFragment : BaseDialogFragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentCreateBinding
    private lateinit var viewModel: CreateViewModel
    private var googleMap: GoogleMap? = null
    private var currentMarker: Marker? = null

    private var hasLocationPermission: Boolean = false
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

    companion object {
        private const val LATITUDE = "LATITUDE"
        private const val LONGITUDE = "LONGITUDE"
    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    internal fun newInstance(latitude: Double, longitude: Double): CreateFragment {
        val fragment = CreateFragment()
        val args = Bundle()
        args.putDouble(LATITUDE, latitude)
        args.putDouble(LONGITUDE, longitude)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            latitude = arguments!!.getDouble(LATITUDE)
            longitude = arguments!!.getDouble(LONGITUDE)
        }
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.bind(latitude, longitude)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun setUp() {
        initLocationServices()
        viewModel.getDistance().observe(this, Observer<Float> { })
        viewModel.getLatitude().observe(this, Observer<Double> { })
        viewModel.getLongitude().observe(this, Observer<Double> { })
        viewModel.getLabel().observe(this, Observer<String> { currentMarker?.title = viewModel.getLabel().value })
        viewModel.getAddress().observe(this, Observer<String> { currentMarker?.snippet = viewModel.getAddress().value })

        viewModel.updateAddress(getAddressFromLocation(latitude,longitude))

        RxView.clicks(binding.closeButton)
                .subscribe { dialog.dismiss() }

        RxView.clicks(binding.createButton)
                .subscribe {
                    run {
                        viewModel.saveLocation()
                        dismiss()
                    }
                }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        try {
            val geoCoder = Geocoder(this.activity, Locale.ENGLISH)
            val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val address = addresses[0]
                val streetAddress = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    streetAddress.append(address.getAddressLine(i) + " ")
                }
                return streetAddress.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun initLocationServices() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                currentLocation = locationResult.lastLocation
                viewModel.updateCurrentDistance(currentLocation!!.latitude, currentLocation!!.longitude)
            }
        }
        hasLocationPermission = PermissionUtils.checkLocationSettings(context!!)
        if (hasLocationPermission) {
            setLocationProvider()
        }
        viewModel.getDistance().observe(this, Observer<Float> {})
    }

    @SuppressLint("MissingPermission")
    private fun setLocationProvider() {
        locationClient = LocationServices.getFusedLocationProviderClient(context!!)
        locationRequest = LocationRequest.create()
                .setInterval(60000)
                .setFastestInterval(60000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(this.activity)
        this.googleMap = googleMap
        googleMap?.isBuildingsEnabled = true
        googleMap?.isIndoorEnabled = true
        googleMap?.setOnMapClickListener(this)
        currentMarker = googleMap?.addMarker(MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title(viewModel.getLabel().value)
                .snippet(viewModel.getAddress().value))
        moveCamera(latitude, longitude)
    }

    private fun moveCamera(latitude: Double, longitude: Double) {
        val latLng = LatLng(latitude, longitude)
        // Construct the camera position
        val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .bearing(0f) // face camera north
                .zoom(17f) // initialize zoom
                .tilt(45f) // 45 angle
                .build()
        //move map camera
        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onMapClick(point: LatLng?) {
        if (currentLocation == null) {
            return
        }
        viewModel.updateLocation(point!!.latitude, point.longitude, currentLocation!!.latitude, currentLocation!!.longitude)
        currentMarker?.position = LatLng(point.latitude, point.longitude)
        moveCamera(point.latitude, point.longitude)
        viewModel.updateAddress(getAddressFromLocation(point.latitude, point.longitude))
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.mapView.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        binding.mapView.onLowMemory()
        super.onLowMemory()
    }
}
