package com.demont.michael.en689.grenswaarde

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demont.michael.en689.database.Measurement
import com.demont.michael.en689.database.MeasurementDatabaseDao
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.util.ArrayList


class GrenswaardeViewModel (val database: MeasurementDatabaseDao, application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //variable om met meting te kunnen werken
    private var tometing = MutableLiveData<Measurement?>()

    var gwT : String
    var numNum : Int
    lateinit var para:FloatArray

    private val _wayCOMP = MutableLiveData<Boolean>()
    val wayCOMP : LiveData<Boolean> get() = _wayCOMP

    private val _wayNC = MutableLiveData<Boolean>()
    val wayNC : LiveData<Boolean> get() = _wayNC

    private val _wayDoubt = MutableLiveData<Boolean>()
    val wayDoubt : LiveData<Boolean> get() = _wayDoubt

    private val _next = MutableLiveData<Boolean>()
    val next : LiveData<Boolean> get() = _next

    init {  initializeMeasurement()

            gwT =""
            numNum = -1
            _wayCOMP.value = false
            _wayNC.value = false
            _wayDoubt.value = false
            _next.value = false
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
    fun aanpassenWaarden(name:String, work:String, elem:String, res:ArrayList<Double>, limit:Double, conf:Int) {
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

    fun doorsturen() {

        var gw:Float? = gwT.toFloatOrNull()

        if (gw == null) {
        //    return @setOnClickListener
        }

         else {

            if (para[numNum - 1] > gw) {
                _wayNC.value = true
            }
             else if (para[numNum - 1] < 0.1 * gw) {
               _wayCOMP.value = true
            }
            else if (numNum >= 4 && para[numNum - 1] < 0.15 * gw) {
                _wayCOMP.value = true
            }
            else if (numNum >= 5 && para[numNum - 1] < 0.2 * gw) {
                _wayCOMP.value = true
            }
            else if (numNum > 5) {
                _next.value = true
            }
            else {
                _wayDoubt.value = true
            }
        }
    }
}
