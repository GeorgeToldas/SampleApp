package com.toldas.sampleapplication.ui.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class AbstractViewHolder<V>(itemView: View, protected var context: Context) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: V)
}