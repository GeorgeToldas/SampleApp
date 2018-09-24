package com.toldas.sampleapplication.ui.base

import android.content.Context
import android.support.v7.widget.RecyclerView

abstract class AbstractListAdapter<V, K : AbstractViewHolder<V>>(
        protected var context: Context,
        protected var resourceID: Int,
        protected var items: ArrayList<V>,
        protected var onItemClickListener: OnItemClickListener<V>?
) : RecyclerView.Adapter<K>() {

    override fun onBindViewHolder(holder: K, position: Int) {
        holder.bind(getItem(position))
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): V {
        return items[position]
    }

    fun addEntity(index: Int, entity: V) {
        items.add(index, entity)
        notifyItemInserted(index)
    }

    fun deleteEntity(item: V) {
        val index = items.indexOf(item)
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    interface OnItemClickListener<V> {
        fun onItemClick(item: V)
    }
}