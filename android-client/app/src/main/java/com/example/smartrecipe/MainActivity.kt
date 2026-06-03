package com.example.smartrecipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tìm thanh điều hướng
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val topToolbar = findViewById<MaterialToolbar>(R.id.topToolbar)

        // Gắn thanh menu với bộ điều khiển chuyển trang
        bottomNav.setupWithNavController(navController)

        topToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    true
                }
                R.id.action_profile -> {
                    true
                }
                else -> false
            }
        }

        topToolbar.setNavigationOnClickListener {
        }

    }
}