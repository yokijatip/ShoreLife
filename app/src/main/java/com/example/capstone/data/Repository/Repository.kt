package com.example.capstone.data.Repository

import com.example.capstone.data.response.DetailResponse
import com.example.capstone.data.response.ItemResponse
import com.example.capstone.data.retrofit.ApiService
import retrofit2.Response

class Repository(private val apiService: ApiService) {

    suspend fun getAllItem(): Response<List<ItemResponse>> {
        return apiService.getAllItem()
    }

    suspend fun getDetailById(id: Int): DetailResponse {
        return apiService.getDetailById(id)
    }


}