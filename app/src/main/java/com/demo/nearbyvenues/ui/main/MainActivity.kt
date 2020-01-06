package com.demo.nearbyvenues.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.demo.nearbyvenues.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tbMain)
        setupActionBarWithNavController(findNavController(R.id.root_nav_fragment),
            AppBarConfiguration.Builder(setOf(R.id.fragmentMap)).build())
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.root_nav_fragment).navigateUp()
    }
}
