package com.example.capstone.ui.splashscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.capstone.R
import com.example.capstone.data.pref.FishPreference
import com.example.capstone.data.pref.dataStore
import com.example.capstone.ui.main.MainActivity
import com.example.capstone.ui.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            lifecycleScope.launch {
                val token = getTokenDataStore()
                if (token.isNotEmpty()) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                } else {
                    startActivity(
                        Intent(
                            this@SplashScreenActivity,
                            WelcomeActivity::class.java
                        )
                    )
                }
                finish()
            }
        }, SPLASH_TIME_OUT)

        setupView()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private suspend fun getTokenDataStore():String {
        val dataStore = FishPreference.getInstance(this.dataStore)
        return dataStore.getToken()
    }
}