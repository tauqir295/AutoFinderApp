package com.auto.finder.network.datasource

import com.auto.finder.model.Auto
import retrofit2.Response

/**
 * Interface for API class
 */
interface AppDataSource {
    suspend fun getAutoManufacturerList(
        page: Int,
        pageSize: Int,
        waKey: String
    ): Response<Auto>

    suspend fun getMainTypes(
        manufacturer: String,
        waKey: String
    ): Response<Auto>

    suspend fun getBuildDates(
        manufacturer: String,
        mainType: String,
        waKey: String
    ): Response<Auto>
}