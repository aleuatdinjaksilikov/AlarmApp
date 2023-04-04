package com.example.myalarmapp.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.myalarmapp.R
import com.example.myalarmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var parentNavController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parentNavController = (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
        NavigationUI.setupWithNavController(binding.bottomNavView,parentNavController,false)
    }
}