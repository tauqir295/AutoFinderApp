package com.auto.finder.ui

import android.view.View

/**
 * custom interface to pass the click event on item to the listener
 */
interface OnRecyclerItemClickListener {
    fun onItemClick(
        item: View?,
        auto: String,
        position: Int
    )
}