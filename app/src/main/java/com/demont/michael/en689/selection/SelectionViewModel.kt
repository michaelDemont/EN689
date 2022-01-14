package com.demont.michael.en689.selection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demont.michael.en689.database.Measurement
import com.demont.michael.en689.database.MeasurementDatabaseDao
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.util.ArrayList
import kotlin.math.ln
import kotlin.math.sqrt

class SelectionViewModel (val database: MeasurementDatabaseDao, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //variable om met meting te kunnen werken
    private var tometing = MutableLiveData<Measurement?>()

    var numM: Int
    lateinit var resM:FloatArray
    var gwM: Float

    val mapM = mapOf (6 to 2.187, 7 to 2.120, 8 to 2.072, 9 to 2.035, 10 to 2.005,
        11 to 1.981, 12 to 1.961, 13 to 1.944, 14 to 1.929, 15 to 1.917,
        16 to 1.905, 17 to 1.895, 18 to 1.886, 19 to 1.878, 20 to 1.870,
        21 to 1.863, 22 to 1.857, 23 to 1.851, 24 to 1.846, 25 to 1.841,
        26 to 1.836, 27 to 1.832, 28 to 1.828, 29 to 1.824, 30 to 1.820)

    init { initializeMeasurement()
            numM = -1
            gwM = (-1).toFloat()
         }


    //initialiseren variabele
    private fun initializeMeasurement() {
        uiScope.launch {
            tometing.value = getMeasurementFromDatabase("", LocalDateTime.now())
        }
    }
    //haal bestaande meting of null op
    private suspend fun getMeasurementFromDatabase(str : String, dat : LocalDateTime) : Measurement? {
        return withContext(Dispatchers.IO) {
            var meting = database.getHuidigeMeting(str, dat)
            meting
        }
    }


    //Nieuwe meting creëren
    fun createMeasurement() {
        uiScope.launch {
            val newMeting = Measurement()
            insert(newMeting)
            tometing.value = getMeasurementFromDatabase("", newMeting.date)
        }
    }
    private suspend fun insert (met : Measurement) {
        withContext(Dispatchers.IO) {
            database.insert(met)
        }
    }


    //Waarden gecreëerde meting updaten
    fun aanpassenWaarden(name:String, work:String, elem:String, res: ArrayList<Double>, limit:Double, conf:Int) {
        uiScope.launch {
            val oldMet = tometing.value ?: return@launch
            oldMet.naam = name
            oldMet.werkpost = work
            oldMet.element =elem
            oldMet.results = res
            oldMet.grenswaarde = limit
            oldMet.conform = conf
            update(oldMet)
        }
    }
    private suspend fun update(met : Measurement) {
        withContext(Dispatchers.IO) {
            database.update(met)
        }
    }


    //gemiddelde
    fun calLnGM(): Float {
        var sum: Float = 0.0F
        for (resultaat in resM) {
                sum += ln(resultaat)
        }
        return sum/numM
    }
    fun calAM(): Float {
        var sum: Float = 0.0F
        for (resultaat in resM) {
            sum += resultaat
        }
        return sum/numM
    }

    //standaarddeviatie
    fun calLnGSD(): Float {
        var verschil: Float = 0.0F
        var kwad: Float = 0.0F
        var tot: Float = 0.0F
        for (resultaat in resM) {
            verschil = (ln(resultaat) -calLnGM())
            kwad = verschil*verschil
            tot += kwad
        }
        return sqrt(tot/(numM-1))
    }
    fun calSD(): Float {
        var verschil: Float = 0.0F
        var kwad: Float = 0.0F
        var tot: Float = 0.0F
        for (resultaat in resM) {
            verschil = (resultaat - calAM())
            kwad = verschil*verschil
            tot += kwad
        }
        return sqrt(tot/(numM-1))
    }

    //bepalen UR
    fun calnUR(): Float {
        return ((ln(gwM) -calLnGM())/calLnGSD())
    }
    fun calUR(): Float {
        return ((gwM - calAM()) / calSD())
    }

    //haal overeenkomstige UT waarde
    fun searchUT(): Double {
        return mapM.get(numM)!!
    }

    //Zijn de metingen comform?
    fun ntestStat(): Boolean {
        return (calnUR()> searchUT())
    }
    fun testStat(): Boolean {
        return (calUR()> searchUT())
    }
}