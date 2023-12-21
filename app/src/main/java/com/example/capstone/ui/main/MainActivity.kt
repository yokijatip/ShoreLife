package com.example.capstone.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.example.capstone.R
import com.example.capstone.databinding.ActivityMainBinding
import com.example.capstone.ui.camera.CameraFragment
import com.example.capstone.ui.fact.FactFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.apply {
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        val fragment = HomeFragment.instance()
                        replaceFragment(fragment)
                    }

                    R.id.camera -> {
                        val fragment = CameraFragment.instance()
                        replaceFragment(fragment)
                    }

                    R.id.fact -> {
                        val fragment = FactFragment.instance()
                        replaceFragment(fragment)
                    }
                }
                bottomNavigation.menu.findItem(it.itemId)?.isChecked = true
                false
            }
        }

        val fragment = HomeFragment.instance()
        replaceFragment(fragment)

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.content, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }
}