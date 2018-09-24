package com.toldas.sampleapplication.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.toldas.sampleapplication.R
import com.toldas.sampleapplication.data.model.MapLocation
import com.toldas.sampleapplication.databinding.ItemListLocationBinding
import com.toldas.sampleapplication.ui.listeners.ItemClickListener

class LocationAdapter(
        val onClickListener: ItemClickListener<MapLocation>
) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private var locations: List<MapLocation>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListLocationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list_location, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations!![position])
    }

    override fun getItemCount(): Int {
        return if (locations == null) {
            0
        } else {
            locations!!.size
        }
    }

    fun setList(data: List<MapLocation>?) {
        locations = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(
            private val binding: ItemListLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var viewModel = LocationItemViewModel()
        private lateinit var item: MapLocation

        init {

            @Suppress("Unused")
            RxView.clicks(itemView)
                    .subscribe { _ -> onClickListener.onClick(item) }
        }

        fun bind(item: MapLocation) {
            this.item = item
            viewModel.bind(item)
            binding.viewModel = viewModel
        }


    }
}