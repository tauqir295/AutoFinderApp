package com.auto.finder.network.datasource

import com.auto.finder.model.Auto
import com.auto.finder.network.ApiInterface
import retrofit2.Response
import retrofit2.http.Query

/**
 * Interface implementation for AppDataSource
 * @param - [ApiInterface] - class is used for fetching data from server
 */
class RemoteDataSource(private val apiInterface: ApiInterface) : AppDataSource {

    override suspend fun getAutoManufacturerList(
        page: Int,
        pageSize: Int,
        waKey: String
    ): Response<Auto> =
        apiInterface.getAutoManufacturers(page, pageSize, waKey)

    override suspend fun getMainTypes(
        manufacturer: String,
        waKey: String
    ): Response<Auto> = apiInterface.getMainTypes(manufacturer, waKey)

    override suspend fun getBuildDates(
        manufacturer: String,
        mainType: String,
        waKey: String
    ): Response<Auto> = apiInterface.getBuildDates(manufacturer, mainType, waKey)
}