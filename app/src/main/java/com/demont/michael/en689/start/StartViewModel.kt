package com.demont.michael.en689.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartViewModel :ViewModel() {

    var bedrijf:String = ""
    var number:Int? = 0
    var funct:String = ""

    private val _inputOk = MutableLiveData<Boolean>()
    val inputOk : LiveData<Boolean> get() = _inputOk

    private val _nameMissing = MutableLiveData<Boolean>()
    val nameMissing : LiveData<Boolean> get() = _nameMissing
    private val _numberFout = MutableLiveData<Int>()
    val numberFout : LiveData<Int> get() = _numberFout

    init {
            _inputOk.value = false
            _nameMissing.value = false
            _numberFout.value = 0
            Log.i("StartViewModel", "StartViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("StartViewModel", "StartViewModel destroyed!")
    }

    fun verify () {
        _nameMissing.value = false
        _numberFout.value = 0

        if (bedrijf == "") {
            _nameMissing.value = true
            //
        }
        if (number == null) {
            _numberFout.value = 1
        }
        if (number != null && number!! < 3) {
            _numberFout.value = 2
        }
        else if (bedrijf != null && bedrijf != "" && number!= null && number!! >= 3) {
          _inputOk.value = true
        }

    }

}

