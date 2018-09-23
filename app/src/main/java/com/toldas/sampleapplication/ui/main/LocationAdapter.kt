package com.toldas.sampleapplication.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.databinding.ItemListLocationBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private lateinit var locationList: ArrayList<MapLocation>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListLocationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list_location, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    class ViewHolder(private val binding: ItemListLocationBinding) : RecyclerView.ViewHolder(binding.root) {

        private var viewModel = LocationItemViewModel()

        fun bind(locationItem: MapLocation) {
            viewModel.bind(locationItem)
            binding.viewModel = viewModel
        }
    }
}