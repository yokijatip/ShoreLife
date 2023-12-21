package com.example.capstone.data.retrofit


import com.example.capstone.data.response.DetailResponse
import com.example.capstone.data.response.ItemResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("allbatik")
    suspend fun getAllItem(): Response<List<ItemResponse>>

    @GET("batik/{id}")
    suspend fun getDetailById(@Path("id") id: Int): DetailResponse


}