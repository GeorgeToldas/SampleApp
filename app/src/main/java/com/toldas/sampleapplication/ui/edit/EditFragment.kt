package com.toldas.sampleapplication.ui.edit


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
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
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.databinding.FragmentEditBinding
import com.toldas.sampleapplication.ui.base.BaseDialogFragment
import com.toldas.sampleapplication.utils.PermissionUtils
import javax.inject.Inject

class EditFragment : BaseDialogFragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentEditBinding
    private lateinit var viewModel: EditViewModel
    private var googleMap: GoogleMap? = null
    private var currentMarker: Marker? = null

    private var hasLocationPermission: Boolean = false
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLocation: Location

    companion object {
        private const val OBJECT = "ID"
    }

    private lateinit var mapLocation: MapLocation

    internal fun newInstance(mapLocation: MapLocation): EditFragment {
        val fragment = EditFragment()
        val args = Bundle()
        args.putParcelable(OBJECT, mapLocation)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mapLocation = arguments!!.getParcelable(OBJECT)
        }
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.bind(mapLocation)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        initLocationServices()

        viewModel.getDistance().observe(this, Observer<Float> { })
        viewModel.getLatitude().observe(this, Observer<Double> { })
        viewModel.getLongitude().observe(this, Observer<Double> { })
        viewModel.getLabel().observe(this, Observer<String> { currentMarker?.title = viewModel.getLabel().value })
        return binding.root
    }

    private fun initLocationServices() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                currentLocation = locationResult.lastLocation
                viewModel.updateCurrentDistance(currentLocation.latitude, currentLocation.longitude)
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
                .position(LatLng(mapLocation.latitude, mapLocation.longitude))
                .title(mapLocation.label))
        moveCamera(mapLocation.latitude, mapLocation.longitude)
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
        viewModel.updateLocation(point!!.latitude, point.longitude, currentLocation.latitude, currentLocation.longitude)
        currentMarker?.position = LatLng(point.latitude, point.longitude)
        moveCamera(point.latitude, point.longitude)
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
