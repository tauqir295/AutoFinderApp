package com.auto.finder.utils

import android.util.Log
import com.auto.finder.BuildConfig

object Logger {

    /**
     * check debug flavor
     */
    private const val TAG = "Auto finder"
    private fun enableLog():Boolean {
        return BuildConfig.DEBUG
    }

    fun d (tag: String = TAG, msg:String) {
        when {
            enableLog() -> Log.d(tag, msg)
        }
    }

    fun e (tag: String = TAG, msg:String) {
        when {
            enableLog() -> Log.d(tag, msg)
        }
    }
}