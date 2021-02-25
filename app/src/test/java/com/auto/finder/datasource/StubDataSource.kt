package com.auto.finder.datasource

import com.auto.finder.model.Auto
import com.auto.finder.network.datasource.AppDataSource
import com.google.gson.Gson
import retrofit2.Response

/**
 * Interface implementation for AppDataSource
 * This class is used for reading data from mock file present debug/resources folder
 */
class StubDataSource : AppDataSource {
    override suspend fun getAutoManufacturerList(
        page: Int,
        pageSize: Int,
        waKey: String
    ): Response<Auto> {
        return readFromLocalFile("mock_manufacturer.json")
    }

    override suspend fun getMainTypes(manufacturer: String, waKey: String): Response<Auto> {
        return readFromLocalFile("mock_main_types.json")
    }

    override suspend fun getBuildDates(
        manufacturer: String,
        mainType: String,
        waKey: String
    ): Response<Auto> {
        return readFromLocalFile("mock_build_dates.json")
    }

    private fun readFromLocalFile(fileName: String): Response<Auto> {
        val classLoader = Thread.currentThread().contextClassLoader
        classLoader?.getResourceAsStream(fileName).use {
            val rawData = it?.reader()?.readText()
            val data = Gson().fromJson(rawData, Auto::class.java)
            return Response.success(data)
        }
    }

}