package com.toldas.sampleapplication.ui.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.databinding.ItemListLocationBinding
import com.toldas.sampleapplication.ui.base.AbstractListAdapter
import com.toldas.sampleapplication.ui.base.AbstractViewHolder

class LocationAdapter(
        context: Context, resourceID: Int, items: ArrayList<MapLocation>, onItemClickListener: OnItemClickListener<MapLocation>?
) : AbstractListAdapter<MapLocation, LocationAdapter.ViewHolder>(context, resourceID, items, onItemClickListener) {

    private lateinit var locationList: ArrayList<MapLocation>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListLocationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list_location, parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    inner class ViewHolder(
            private val binding: ItemListLocationBinding, context: Context
    ) : AbstractViewHolder<MapLocation>(binding.root, context) {

        private var viewModel = LocationItemViewModel()
        private lateinit var item: MapLocation

        init {

            RxView.clicks(itemView)
                    .subscribe { _ -> onItemClickListener?.onItemClick(item) }
        }

        override fun bind(item: MapLocation) {
            this.item = item
            viewModel.bind(item)
            binding.viewModel = viewModel
        }


    }
}