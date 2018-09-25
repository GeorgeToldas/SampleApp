package com.toldas.sampleapplication.utils

import android.databinding.BindingAdapter
import android.widget.TextView
import java.util.*

@BindingAdapter("locationDistance")
fun setDistance(view: TextView, distance: Float) {
    view.text = LocationUtils.filterDistance(distance)
}

@BindingAdapter("locationDistanceShort")
fun setDistanceShort(view: TextView, distance: Float) {
    view.text = LocationUtils.filterDistanceShort(distance)
}

@BindingAdapter("coordinates")
fun setText(view: TextView, value: Double) {
    view.text = String.format(Locale.US, "%.4f", value)
}