package com.auto.finder.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base view holder class
 * @param - [View] - variable use for binding the UI with adapter
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(auto: String)
}