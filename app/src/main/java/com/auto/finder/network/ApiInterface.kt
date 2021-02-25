package com.auto.finder.network

import com.auto.finder.model.Auto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * retrofit service interface for defining the api call methods
 */
interface ApiInterface {

    @GET("/v1/car-types/manufacturer")
    suspend fun getAutoManufacturers(
        @Query("page") page: Int,
        @Query("pageSize") Int: Int,
        @Query("wa_key") waKey: String
    ): Response<Auto>

    @GET("/v1/car-types/main-types")
    suspend fun getMainTypes(
        @Query("manufacturer") manufacturer: String,
        @Query("wa_key") waKey: String
    ): Response<Auto>

    @GET("/v1/car-types/built-dates")
    suspend fun getBuildDates(
        @Query("manufacturer") manufacturer: String,
        @Query("main-type") mainType: String,
        @Query("wa_key") waKey: String
    ): Response<Auto>
}