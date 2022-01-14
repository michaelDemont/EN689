package com.demont.michael.en689.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface MeasurementDatabaseDao {

    @Insert
    fun insert(measure:Measurement)

    @Update
    fun update(measure: Measurement)

    //delete 1 meting
    @Delete
    fun delete(measure: Measurement)
    //delete metingen van een bedrijf
    @Query("DELETE FROM metingen_chemische_agentia_table WHERE UPPER(naam_bedrijf) = UPPER(:naamBedrijf)")
    fun deleteCompany(naamBedrijf : String)
    //delete all
    @Query("DELETE FROM metingen_chemische_agentia_table")
    fun clear()

    //geef lijst van metingen bedrijf
    @Query("SELECT * from metingen_chemische_agentia_table WHERE UPPER(naam_bedrijf) = UPPER(:naamBedrijf) ORDER BY datum DESC")
    fun getMetingen (naamBedrijf : String) : LiveData<List<Measurement?>>
    //selecteer 1 meting
    @Query("SELECT * from metingen_chemische_agentia_table WHERE UPPER(naam_bedrijf) = UPPER(:naamBedrijf) AND datum = :tijdstip")
    fun getHuidigeMeting (naamBedrijf : String, tijdstip : LocalDateTime) : Measurement?
}