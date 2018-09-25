package com.toldas.sampleapplication.utils

import android.databinding.BindingAdapter
import android.widget.TextView

@BindingAdapter("locationDistance")
fun setDistance(view: TextView, distance: Float) {
    view.text = LocationUtils.filterDistance(distance)
}