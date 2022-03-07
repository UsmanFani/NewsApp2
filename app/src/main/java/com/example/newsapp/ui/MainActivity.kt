package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager
            .findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        binding.bottomNavView.setupWithNavController(navHostFragment.navController)

    }

}