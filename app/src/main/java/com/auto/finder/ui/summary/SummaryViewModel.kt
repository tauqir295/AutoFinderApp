package com.auto.finder.ui.summary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Custom ViewModel class
 */
@HiltViewModel
class SummaryViewModel @Inject constructor(): ViewModel() {
    var mainType: String? = null
    var mainTypeKey: String? = null
    var manufacturerKey: String? = null
    var manufacturer: String? = null
    var dateKey: String? = null
    var date: String? = null
}