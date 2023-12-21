package com.example.capstone.data.di


import android.content.Context
import com.example.capstone.data.Repository.Repository
import com.example.capstone.data.pref.FishPreference
import com.example.capstone.data.pref.dataStore
import com.example.capstone.data.retrofit.ApiConfig
import com.example.capstone.data.retrofit.ApiService


object Injection {

    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }

    fun proviceApiService(): ApiService {
        return ApiConfig.getApiService()
    }
}