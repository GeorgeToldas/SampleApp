package com.toldas.sampleapplication.ui.details


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.databinding.FragmentDetailsBinding

import com.toldas.sampleapplication.ui.base.BaseDialogFragment
import io.realm.RealmResults
import javax.inject.Inject

class DetailsFragment : BaseDialogFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private var googleMap: GoogleMap? = null

    companion object {
        private const val OBJECT = "ID"
        private const val LATITUDE = "LATITUDE"
        private const val LONGITUDE = "LONGITUDE"
    }

    private lateinit var mapLocation: MapLocation
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    internal fun newInstance(mapLocation: MapLocation, latitude: Double, longitude: Double): DetailsFragment {
        val fragment = DetailsFragment()
        val args = Bundle()
        args.putParcelable(OBJECT, mapLocation)
        args.putDouble(LATITUDE, latitude)
        args.putDouble(LONGITUDE, longitude)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mapLocation = arguments!!.getParcelable(OBJECT)
            latitude = arguments!!.getDouble(LATITUDE)
            longitude = arguments!!.getDouble(LONGITUDE)
        }
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.bind(mapLocation)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun setUp() {
        viewModel.getDistance().observe(this, Observer<Float> { })
        viewModel.getLatitude().observe(this, Observer<Double> { })
        viewModel.getLongitude().observe(this, Observer<Double> { })
        viewModel.getLabel().observe(this, Observer<String> { })
        viewModel.getAddress().observe(this, Observer<String> { })
        viewModel.getLocationList().observe(this, Observer<RealmResults<MapLocation>> { locations -> addMarkers(locations) })


        RxView.clicks(binding.closeButton)
                .subscribe { dialog.dismiss() }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(this.activity)
        this.googleMap = googleMap
        googleMap?.isBuildingsEnabled = true
        googleMap?.isIndoorEnabled = true
        googleMap?.setOnMarkerClickListener(this)
        googleMap?.addMarker(MarkerOptions()
                .position(LatLng(mapLocation.latitude, mapLocation.longitude))
                .title(mapLocation.label)
                .snippet(mapLocation.address))
        moveCamera(mapLocation.latitude, mapLocation.longitude)
    }

    private fun addMarkers(locations: List<MapLocation>?) {
        for (location in locations!!) {
            googleMap?.addMarker(MarkerOptions()
                    .position(LatLng(location.latitude, location.longitude))
                    .title(location.label)
                    .snippet(location.address))
                    ?.tag = location.id
        }
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

    override fun onMarkerClick(marker: Marker?): Boolean {
        viewModel.updateLocation(
                marker!!.position.latitude, marker.position.longitude, latitude, longitude)
        viewModel.getLabel().value = marker.title
        viewModel.getAddress().value = marker.snippet
        viewModel.updateCurrentDistance(latitude, longitude)
        moveCamera(marker.position.latitude,marker.position.longitude)
        return false
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
