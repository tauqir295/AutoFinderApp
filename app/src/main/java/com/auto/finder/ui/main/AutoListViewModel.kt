package com.auto.finder.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auto.finder.model.Auto
import com.auto.finder.network.datasource.AppDataSource
import com.auto.finder.utils.AutoFinderNetworkHelper
import com.auto.finder.utils.Resource
import com.auto.finder.utils.WA_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * Custom ViewModel class
 */
@HiltViewModel
class AutoListViewModel @Inject constructor(
    private val dataSource: AppDataSource,
    private val networkHelper: AutoFinderNetworkHelper
) : ViewModel() {

    private val _auto = MutableLiveData<Resource<Auto>>()
    val auto: LiveData<Resource<Auto>>
        get() = _auto

    init {
        //initiating the API calls
        fetchManufacturerList(0, 15, WA_KEY)
    }

    /**
     * get manufactures from API
     */
    fun fetchManufacturerList(
        page: Int,
        pageSize: Int,
        waKey: String
    ) {
        viewModelScope.launch {
            _auto.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    dataSource.getAutoManufacturerList(page, pageSize, waKey).let {
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