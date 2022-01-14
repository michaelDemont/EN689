package com.demont.michael.en689.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@Entity (tableName = "metingen_chemische_agentia_table", primaryKeys = ["naam_bedrijf", "datum"])
data class Measurement(

    //meetgegevens
    @ColumnInfo(name = "naam_bedrijf")
        var naam : String = "",
    @ColumnInfo(name = "functie")
        var werkpost : String = "",
    @ColumnInfo(name = "agens")
        var element : String = "",
    @ColumnInfo(name = "lijst_waarden")
        var results : ArrayList<Double> = ArrayList(),
    @ColumnInfo(name = "geldende_grenswaarde")
        var grenswaarde : Double = -1.00,

    //auto-generated
    @ColumnInfo(name = "conformiteit")
    var conform : Int = -1,

    @ColumnInfo(name = "datum")
    var date: LocalDateTime = LocalDateTime.now()

)