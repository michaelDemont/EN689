package com.demont.michael.en689

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    // variabele naam is voorzien voor eventueel export van gegevens om een rapport te maken
    private var aantal:Int = 0
    private var naam:String = ""
    private var results:MutableList<Double> = ArrayList()
    private var grensWaarde:Double = 0.00
    private var cheater:Boolean = false
    private var meanM:Double =0.00

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

    //Setters
    fun setAantal (numbers:Int) {
        aantal = numbers
    }
    fun setNaam (bedrijf:String) {
        naam = bedrijf
    }
    fun setResults (measureValues:MutableList<Double>) {
        results = measureValues.sorted().toMutableList()
    }
    fun setGW (valeurLim:Double) {
        grensWaarde = valeurLim
    }
    fun setCheat (cheat:Boolean) {
        cheater = cheat
    }
    fun setMean (mean:Double) {
        meanM = mean
    }

    //getters
    fun getAantal() : Int {
        return aantal
    }
    fun getResults() : List<Double> {
        return results
    }
    fun getGW (): Double {
        return grensWaarde
    }
    fun getCheat (): Boolean {
        return cheater
    }
    fun getMean (): Double {
        return meanM
    }

    fun getNaam(): String {
        return naam
    }


}
