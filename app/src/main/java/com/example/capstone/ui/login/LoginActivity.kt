package com.example.capstone.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.capstone.data.pref.FishPreference
import com.example.capstone.data.pref.dataStore
import com.example.capstone.databinding.ActivityLoginBinding
import com.example.capstone.ui.main.MainActivity
import com.example.capstone.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            authLoginFirebase(email, password)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
        playAnimation()
    }

    private fun authLoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val user = auth.currentUser
                user?.getIdToken(true)?.addOnCompleteListener { tokenId ->
                    if (tokenId.isSuccessful) {
                        val token = tokenId.result?.token
                        Log.d("Token JWT", "My JWT Token : $token")
                        lifecycleScope.launch {
                            if (token != null) {
                                saveTokenDataStore(token)
                            }
                        }
                        onSuccessLogin()
                    } else {
                        onFailureLogin()
                    }
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val forgotPassword = ObjectAnimator.ofFloat(binding.forgotPassword, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val noHaveAccount = ObjectAnimator.ofFloat(binding.noHaveAccount, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                forgotPassword,
                login,
                noHaveAccount
            )
            startDelay = 100
        }.start()
    }

    private fun onFailureLogin() {
        Toast.makeText(this, "GAGAL LOGIN", Toast.LENGTH_LONG).show()
    }

    private fun onSuccessLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private suspend fun saveTokenDataStore(token: String) {
        val dataStore = FishPreference.getInstance(this.dataStore)
        dataStore.saveToken(token)
    }

}