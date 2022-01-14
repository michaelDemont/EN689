package com.demont.michael.en689.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputViewModel: ViewModel() {

    var iteratie: Int
    var evt:String

    private val _waarde = MutableLiveData<Float>()
    val waarde : LiveData<Float> get() = _waarde

    private val _teller = MutableLiveData<Int>()
    val teller : LiveData<Int> get() = _teller

    private val _inputOK = MutableLiveData<Boolean>()
    val inputOK : LiveData<Boolean> get() = _inputOK

    init {
        _teller.value = 1
        iteratie = -1
        evt = ""
        _inputOK.value = false
    }

    fun check() {
        if (evt.toFloatOrNull() == null) {
    //        return @setOnClickListener
        }
        else {  _waarde.value = evt.toFloat()
                _teller.value = _teller.value?.plus(1)

                if (_teller.value!! > iteratie) {
                    _inputOK.value = true
                }
            }

    }
}