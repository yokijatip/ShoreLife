package com.example.capstone.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "storyApp")

class FishPreference private constructor(private val context: DataStore<Preferences>){

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