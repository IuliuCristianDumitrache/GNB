package com.apps.gmb.data.networking

import com.apps.gmb.data.models.Conversion
import com.apps.gmb.data.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("/rates.json")
    suspend fun getConversions(): Response<ArrayList<Conversion>>

    @Headers("Content-Type: application/json")
    @GET("/transactions.json")
    suspend fun getProducts(): Response<ArrayList<Product>>
}