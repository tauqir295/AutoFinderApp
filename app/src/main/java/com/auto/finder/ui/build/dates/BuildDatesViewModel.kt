package com.auto.finder.ui.build.dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auto.finder.model.Auto
import com.auto.finder.network.datasource.AppDataSource
import com.auto.finder.utils.AutoFinderNetworkHelper
import com.auto.finder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * Custom ViewModel class
 */
@HiltViewModel
class BuildDatesViewModel @Inject constructor(
    private val dataSource: AppDataSource,
    private val networkHelper: AutoFinderNetworkHelper
) : ViewModel() {

    var mainType: String? = null
    var mainTypeKey: String? = null
    var manufacturerKey: String? = null
    var manufacturer: String? = null
    var filterText: String? = null

    private val _auto = MutableLiveData<Resource<Auto>>()
    val auto: LiveData<Resource<Auto>>
        get() = _auto

    /**
     * get build dates from API
     */
    fun fetchBuildDates(
        manufacturer: String,
        mainType: String,
        waKey: String
    ) {
        viewModelScope.launch {
            _auto.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataSource.getBuildDates(manufacturer, mainType, waKey).let {
                        if (it.isSuccessful) {
                            _auto.postValue(Resource.success(it.body()))
                        } else _auto.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                } catch (e: IOException) {
                    _auto.postValue(Resource.error("Something went wrong.", null))
                }

            } else _auto.postValue(Resource.error("Something went wrong.", null))
        }
    }

    /**
     * clear data in view model
     */
    fun clearViewModelData() {
        _auto.value = null
    }
}