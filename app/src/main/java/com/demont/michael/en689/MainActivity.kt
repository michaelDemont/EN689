package com.demont.michael.en689

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

        // Zet de Up-button maar overal dus ook in het Startscherm
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Regelt de functie van de Up-button
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp() }

}
