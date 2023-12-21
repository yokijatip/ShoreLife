package com.example.capstone.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "storyApp")

class FishPreference private constructor(private val dataStore: DataStore<Preferences>){


    private val TOKEN_AUTH_KEY = stringPreferencesKey("token_auth")

    suspend fun saveToken(token: String) {
        dataStore.edit {preferences ->
            preferences[TOKEN_AUTH_KEY] = token
        }
    }

    suspend fun getToken(): String {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_AUTH_KEY] ?: ""
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_AUTH_KEY)
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: FishPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): FishPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = FishPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}