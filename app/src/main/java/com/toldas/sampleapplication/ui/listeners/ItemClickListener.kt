package com.toldas.sampleapplication.ui.listeners

interface ItemClickListener<V> {

    fun onItemClick(item: V)
    fun onDeleteClick(item: V)
    fun onEditClick(item: V)
}