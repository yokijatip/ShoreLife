package com.example.capstone.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.data.pref.FishPreference
import com.example.capstone.data.pref.dataStore
import com.example.capstone.databinding.ActivityProfileBinding
import com.example.capstone.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                clearTokenDataStore()
                startActivity(Intent(this@ProfileActivity, WelcomeActivity::class.java))
            }
        }

    }

    private suspend fun clearTokenDataStore() {
        val dataStore = FishPreference.getInstance(dataStore)
        return dataStore.clearToken()
    }
}