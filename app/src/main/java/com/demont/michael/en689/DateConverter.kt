package com.demont.michael.en689


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringFromDate (date:LocalDateTime?) : String {
       var str: String = date!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        return str
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun dateFromString (value:String?) : LocalDateTime {
        val dat = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        return dat
    }

    @TypeConverter
    fun arrayListFromString (value:String?) : ArrayList<Double> {
        val listType : Type = object : TypeToken<ArrayList<Double?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun stringFromArrayList (list:ArrayList<Double?>?) : String {
        return Gson().toJson(list)
    }
}